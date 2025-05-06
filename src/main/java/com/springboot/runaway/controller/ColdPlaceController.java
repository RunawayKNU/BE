package com.springboot.runaway.controller;

import com.springboot.runaway.dto.ColdPlaceDto;
import com.springboot.runaway.service.ColdPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/coldplaces")
@Tag(name = "ColdPlace", description = "한파쉼터 API")
public class ColdPlaceController {
    private final ColdPlaceService service;

    public ColdPlaceController(ColdPlaceService service) {
        this.service = service;
    }

    @Operation(summary = "전체 한파쉼터 리스트 조회")
    @GetMapping("/all")
    public List<ColdPlaceDto> getAll(
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) Double latitude) {
        return service.getAllColdPlaces(longitude, latitude);
    }

    @Operation(summary = "한파쉼터 키워드 검색")
    @GetMapping("/search")
    public List<ColdPlaceDto> search(
            @RequestParam String query,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) Double latitude) {
        return service.searchByAddress(query, longitude, latitude);
    }
}
