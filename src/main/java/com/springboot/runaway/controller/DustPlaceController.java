package com.springboot.runaway.controller;

import com.springboot.runaway.dto.DustPlaceDto;
import com.springboot.runaway.service.DustPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dustplaces")
@Tag(name = "DustPlace", description = "미세먼지 대피소 API")
public class DustPlaceController {

    private final DustPlaceService dustPlaceService;

    public DustPlaceController(DustPlaceService dustPlaceService) {
        this.dustPlaceService = dustPlaceService;
    }

    @Operation(summary = "전체 미세먼지 대피소 리스트 조회")
    @GetMapping("/all")
    public List<DustPlaceDto> getAll(@RequestParam(required = false) Double longitude,
                                     @RequestParam(required = false) Double latitude) {
        return dustPlaceService.getAllDustPlaces(longitude, latitude);
    }

    @Operation(summary = "미세먼지 대피소 키워드 검색")
    @GetMapping("/search")
    public List<DustPlaceDto> searchByAddress(@RequestParam String query,
                                              @RequestParam(required = false) Double longitude,
                                              @RequestParam(required = false) Double latitude) {
        return dustPlaceService.searchByAddress(query, longitude, latitude);
    }
}
