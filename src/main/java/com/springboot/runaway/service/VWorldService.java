package com.springboot.runaway.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class VWorldService {

    private final String apiKey = "9094187B-0046-3CA3-B863-33CFAAA80A8A";

    public double[] convertTmToWgs(double x, double y) {
        String url = String.format(
                "https://api.vworld.kr/req/coordinate?service=coordinate&request=GetTransCoord" +
                        "&crs=EPSG:2097&x=%f&y=%f&output=json&key=%s",
                x, y, apiKey
        );

        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response == null) return new double[]{0.0, 0.0};

            Map<String, Object> res = (Map<String, Object>) response.get("response");
            if (!"OK".equals(res.get("status"))) return new double[]{0.0, 0.0};

            Map<String, Object> result = (Map<String, Object>) res.get("result");
            Map<String, Object> point = (Map<String, Object>) result.get("point");

            double lon = Double.parseDouble(point.get("x").toString());
            double lat = Double.parseDouble(point.get("y").toString());

            return new double[]{lon, lat};
        } catch (Exception e) {
            System.out.println("[VWorld 변환 오류] " + e.getMessage());
            return new double[]{0.0, 0.0};
        }
    }
}
