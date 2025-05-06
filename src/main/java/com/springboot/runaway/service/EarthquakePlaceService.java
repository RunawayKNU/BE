package com.springboot.runaway.service;

import com.springboot.runaway.dto.EarthquakePlaceDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EarthquakePlaceService {

    @Value("${seoul.api.key}")
    private String apiKey;

    // 변수명 통일: restTemplate
    private final RestTemplate restTemplate = new RestTemplate();

    /** 전체 조회 **/
    public List<EarthquakePlaceDto> getAllEarthquakePlaces(Double lon, Double lat) {
        return enrichWithDistance(fetchRawList(), lon, lat);
    }

    /** 키워드 검색 **/
    public List<EarthquakePlaceDto> searchByAddress(String kw, Double lon, Double lat) {
        List<EarthquakePlaceDto> filtered = fetchRawList().stream()
                .filter(dto -> dto.getAddr() != null && dto.getAddr().contains(kw))
                .collect(Collectors.toList());
        return enrichWithDistance(filtered, lon, lat);
    }

    /** 원본 데이터 호출 및 DTO 매핑 **/
    private List<EarthquakePlaceDto> fetchRawList() {
        String datasetCode = "TlEtqkP";
        String url = String.format(
                "http://openapi.seoul.go.kr:8088/%s/json/%s/1/1000",
                apiKey, datasetCode
        );

        @SuppressWarnings("unchecked")
        Map<String,Object> response = restTemplate.getForObject(url, Map.class);
        if (response == null || !response.containsKey(datasetCode)) {
            return Collections.emptyList();
        }

        @SuppressWarnings("unchecked")
        Map<String,Object> wrapper = (Map<String,Object>) response.get(datasetCode);
        @SuppressWarnings("unchecked")
        List<Map<String,Object>> rows = (List<Map<String,Object>>) wrapper.get("row");

        return rows.stream().map(row -> {
            EarthquakePlaceDto dto = new EarthquakePlaceDto();
            // 실제 키 이름에 맞춰서 꺼냅니다
            dto.setDongNm((String) row.get("SGG_NM"));               // 구 이름
            dto.setFcltNm((String) row.get("ACTC_FCLT_NM"));        // 시설 이름
            dto.setAddr((String) row.get("DADDR"));                 // 전체 주소
            dto.setLongitude(parseDoubleSafe(row, "LOT"));    // TM X 좌표
            dto.setLatitude(parseDoubleSafe(row, "LAT"));     // TM Y 좌표
            return dto;
        }).collect(Collectors.toList());
    }

    /** 거리 계산 & 정렬 **/
    private List<EarthquakePlaceDto> enrichWithDistance(List<EarthquakePlaceDto> list, Double lon, Double lat) {
        if (lon == null || lat == null) return list;

        return list.stream()
                .map(dto -> {
                    double dist = calculateHaversineDistance(
                            lon, lat,
                            tmToLon(dto.getLongitude()), tmToLat(dto.getLatitude())
                    );
                    dto.setDistance(dist);
                    return dto;
                })
                .sorted(Comparator.comparingDouble(EarthquakePlaceDto::getDistance))
                .collect(Collectors.toList());
    }

    /** 좌표 변환 헬퍼 **/
    private double tmToLon(double x) { return ((x - 200000) / 88000.0) + 127.0; }
    private double tmToLat(double y) { return ((y - 500000) / 111000.0) + 37.0; }

    /** 널-안전 숫자 파싱 **/
    private double parseDoubleSafe(Map<String,Object> row, String key) {
        Object v = row.get(key);
        if (v == null) return 0d;
        try { return Double.parseDouble(v.toString()); }
        catch (Exception e) { return 0d; }
    }

    /** 하버사인 거리 계산 **/
    private double calculateHaversineDistance(double lon1, double lat1, double lon2, double lat2) {
        double R = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1), dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2)*Math.sin(dLat/2)
                + Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon/2)*Math.sin(dLon/2);
        double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return Math.round(R * c * 10) / 10.0;
    }
}