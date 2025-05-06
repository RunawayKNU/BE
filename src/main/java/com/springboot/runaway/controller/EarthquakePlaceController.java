package com.springboot.runaway.controller;

import com.springboot.runaway.dto.EarthquakePlaceDto;
import com.springboot.runaway.service.EarthquakePlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/earthquakeplaces")
@Tag(name = "EarthquakePlace", description = "지진 옥외대피소 API")
public class EarthquakePlaceController {

    private final EarthquakePlaceService service;

    public EarthquakePlaceController(EarthquakePlaceService service) {
        this.service = service;
    }

    @Operation(summary = "전체 지진 옥외대피소 리스트 조회")
    @GetMapping("/all")
    public List<EarthquakePlaceDto> getAll(
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) Double latitude) {
        return service.getAllEarthquakePlaces(longitude, latitude);
    }

    @Operation(summary = "지진 옥외대피소 키워드 검색")
    @GetMapping("/search")
    public List<EarthquakePlaceDto> search(
            @RequestParam String query,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) Double latitude) {
        return service.searchByAddress(query, longitude, latitude);
    }
}