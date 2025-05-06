package com.springboot.runaway.service;

import com.springboot.runaway.dto.ColdPlaceDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ColdPlaceService {

    @Value("${seoul.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<ColdPlaceDto> getAllColdPlaces(Double lon, Double lat) {
        return enrichWithDistance(fetchRawList(), lon, lat);
    }

    public List<ColdPlaceDto> searchByAddress(String query, Double lon, Double lat) {
        List<ColdPlaceDto> filtered = fetchRawList().stream()
                .filter(dto -> dto.getAddr() != null && dto.getAddr().contains(query))
                .collect(Collectors.toList());
        return enrichWithDistance(filtered, lon, lat);
    }

    private List<ColdPlaceDto> fetchRawList() {
        String datasetCode = "TbGtnCwP";
        String url = String.format("http://openapi.seoul.go.kr:8088/%s/json/%s/1/1000", apiKey, datasetCode);
        @SuppressWarnings("unchecked")
        Map<String, Object> resp = restTemplate.getForObject(url, Map.class);
        if (resp == null || !resp.containsKey(datasetCode)) {
            return Collections.emptyList();
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> wrapper = (Map<String, Object>) resp.get(datasetCode);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> rows = (List<Map<String, Object>>) wrapper.get("row");

        return rows.stream().map(row -> {
            ColdPlaceDto dto = new ColdPlaceDto();

            String lotnoAddr = (String) row.get("LOTNO_ADDR");
            String dongNm = "";
            if (lotnoAddr != null) {
                String[] parts = lotnoAddr.split(" ");
                if (parts.length >= 3) {
                    dongNm = parts[2];
                }
            }
            dto.setDongNm(dongNm);

            dto.setFcltNm((String) row.get("RESTAREA_NM"));
            dto.setAddr((String) row.get("ROAD_NM_ADDR"));
            dto.setCapacity(row.get("UTZTN_PSBLTY_NOPE") != null ? row.get("UTZTN_PSBLTY_NOPE").toString() : "");
            String wdOpen = (String) row.get("WD_OPN_HRM");
            String wdEnd = (String) row.get("WD_END_HRM");
            dto.setWdUtztnHrm(
                    (wdOpen != null ? wdOpen : "") +
                            (wdOpen != null && wdEnd != null ? "~" : "") +
                            (wdEnd != null ? wdEnd : "")
            );
            dto.setLatitude(parseDoubleSafe(row, "LOT"));
            dto.setLongitude(parseDoubleSafe(row, "LAT"));

            return dto;
        }).collect(Collectors.toList());
    }

    private List<ColdPlaceDto> enrichWithDistance(List<ColdPlaceDto> list, Double lon, Double lat) {
        if (lon == null || lat == null) {
            return list;
        }
        return list.stream().map(dto -> {
                    double dist = calculateHaversineDistance(lon, lat, dto.getLongitude(), dto.getLatitude());
                    dto.setDistance(dist);
                    return dto;
                }).sorted(Comparator.comparingDouble(ColdPlaceDto::getDistance))
                .collect(Collectors.toList());
    }

    private double parseDoubleSafe(Map<String, Object> row, String key) {
        Object v = row.get(key);
        if (v == null) {
            return 0d;
        }
        try {
            return Double.parseDouble(v.toString());
        } catch (Exception e) {
            return 0d;
        }
    }

    private double calculateHaversineDistance(double lon1, double lat1, double lon2, double lat2) {
        double R = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Math.round(R * c * 10) / 10.0;
    }
}
