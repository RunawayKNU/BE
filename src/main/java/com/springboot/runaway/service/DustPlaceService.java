package com.springboot.runaway.service;

import com.springboot.runaway.dto.DustPlaceDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DustPlaceService {

    @Value("${seoul.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<DustPlaceDto> getAllDustPlaces(Double longitude, Double latitude) {
        return enrichWithDistance(fetchRawList(), longitude, latitude);
    }

    public List<DustPlaceDto> searchByAddress(String keyword, Double longitude, Double latitude) {
        return enrichWithDistance(
                fetchRawList().stream()
                        .filter(dto -> dto.getAddr() != null && dto.getAddr().contains(keyword))
                        .collect(Collectors.toList()),
                longitude, latitude
        );
    }

    private List<DustPlaceDto> fetchRawList() {
        String url = String.format("http://openapi.seoul.go.kr:8088/%s/json/shuntPlace/1/1000", apiKey);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response == null || !response.containsKey("shuntPlace")) return List.of();

        Map<String, Object> shuntPlace = (Map<String, Object>) response.get("shuntPlace");
        List<Map<String, Object>> rows = (List<Map<String, Object>>) shuntPlace.get("row");

        return rows.stream().map(row -> {
            DustPlaceDto dto = new DustPlaceDto();
            dto.setDongNm((String) row.get("DONG_NM"));
            dto.setFcltNm((String) row.get("FCLT_NM"));
            dto.setAddr((String) row.get("ADDR"));
            dto.setFcltType((String) row.get("FCLT_TYPE"));
            dto.setUtztnPsbltyNope((String) row.get("UTZTN_PSBLTY_NOPE"));
            dto.setWdUtztnHrm((String) row.get("WD_UTZTN_HRM"));
            dto.setXcrd(Double.parseDouble(row.get("XCRD").toString()));
            dto.setYcrd(Double.parseDouble(row.get("YCRD").toString()));
            return dto;
        }).collect(Collectors.toList());
    }

    private List<DustPlaceDto> enrichWithDistance(List<DustPlaceDto> list, Double longitude, Double latitude) {
        if (longitude == null || latitude == null) return list;

        return list.stream().map(dto -> {
                    double dist = calculateHaversineDistance(
                            longitude, latitude, // 현재 위치
                            tmToLongitude(dto.getXcrd()), tmToLatitude(dto.getYcrd()) // 대피소 위경도로 변환 후 거리 계산
                    );
                    dto.setDistance(dist);
                    return dto;
                }).sorted(Comparator.comparingDouble(DustPlaceDto::getDistance))
                .collect(Collectors.toList());
    }

    private double tmToLongitude(double x) {
        return ((x - 200000) / 88000.0) + 127.0;
    }

    private double tmToLatitude(double y) {
        return ((y - 500000) / 111000.0) + 37.0;
    }

    private double calculateHaversineDistance(double lon1, double lat1, double lon2, double lat2) {
        double R = 6371.0; // 지구 반지름 (km)
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Math.round(R * c * 10) / 10.0;
    }
}
