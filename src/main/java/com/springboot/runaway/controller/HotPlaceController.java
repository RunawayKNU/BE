package com.springboot.runaway.controller;

import com.springboot.runaway.dto.HotPlaceDto;
import com.springboot.runaway.service.HotPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hotplaces")
@Tag(name = "HotPlace", description = "무더위쉼터 API")
public class HotPlaceController {

    private final HotPlaceService service;

    public HotPlaceController(HotPlaceService service) {
        this.service = service;
    }

    @Operation(summary = "전체 무더위쉼터 리스트 조회")
    @GetMapping("/all")
    public List<HotPlaceDto> getAll(
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) Double latitude) {
        return service.getAllHotPlaces(longitude, latitude);
    }

    @Operation(summary = "무더위쉼터 키워드 검색")
    @GetMapping("/search")
    public List<HotPlaceDto> search(
            @RequestParam String query,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) Double latitude) {
        return service.searchByAddress(query, longitude, latitude);
    }
}
