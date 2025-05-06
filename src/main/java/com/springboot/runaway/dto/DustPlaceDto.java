package com.springboot.runaway.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "미세먼지 대피소 정보 DTO")
public class DustPlaceDto {

    @Schema(description = "동 이름", example = "중구")
    private String dongNm;

    @Schema(description = "시설 이름", example = "광희경로당")
    private String fcltNm;

    @Schema(description = "주소", example = "서울특별시 중구 퇴계로 303-9")
    private String addr;

    @Schema(description = "시설 유형", example = "복지회관")
    private String fcltType;

    @Schema(description = "수용 가능 인원", example = "30")
    private String utztnPsbltyNope;

    @Schema(description = "평일 운영 시간", example = "09:00~18:00")
    private String wdUtztnHrm;

    @Schema(description = "X 좌표 (서울시 TM 좌표계)", example = "200445.672843")
    private double xcrd;

    @Schema(description = "Y 좌표 (서울시 TM 좌표계)", example = "551660.313597")
    private double ycrd;

    @Schema(description = "현재 위치로부터 거리 (km)", example = "1.2")
    private double distance;

    @Schema(description = "위도", example = "37.5665")
    private double latitude;

    @Schema(description = "경도", example = "126.9780")
    private double longitude;

    public DustPlaceDto() {
    }

    public DustPlaceDto(String dongNm, String fcltNm, String addr, String fcltType, String utztnPsbltyNope, String wdUtztnHrm, double xcrd, double ycrd, double distance) {
        this.dongNm = dongNm;
        this.fcltNm = fcltNm;
        this.addr = addr;
        this.fcltType = fcltType;
        this.utztnPsbltyNope = utztnPsbltyNope;
        this.wdUtztnHrm = wdUtztnHrm;
        this.xcrd = xcrd;
        this.ycrd = ycrd;
        this.distance = distance;
    }

    public String getDongNm() {
        return dongNm;
    }

    public void setDongNm(String dongNm) {
        this.dongNm = dongNm;
    }

    public String getFcltNm() {
        return fcltNm;
    }

    public void setFcltNm(String fcltNm) {
        this.fcltNm = fcltNm;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getFcltType() {
        return fcltType;
    }

    public void setFcltType(String fcltType) {
        this.fcltType = fcltType;
    }

    public String getUtztnPsbltyNope() {
        return utztnPsbltyNope;
    }

    public void setUtztnPsbltyNope(String utztnPsbltyNope) {
        this.utztnPsbltyNope = utztnPsbltyNope;
    }

    public String getWdUtztnHrm() {
        return wdUtztnHrm;
    }

    public void setWdUtztnHrm(String wdUtztnHrm) {
        this.wdUtztnHrm = wdUtztnHrm;
    }

    public double getXcrd() {
        return xcrd;
    }

    public void setXcrd(double xcrd) {
        this.xcrd = xcrd;
    }

    public double getYcrd() {
        return ycrd;
    }

    public void setYcrd(double ycrd) {
        this.ycrd = ycrd;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
