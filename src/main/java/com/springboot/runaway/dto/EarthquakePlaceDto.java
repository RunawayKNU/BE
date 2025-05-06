package com.springboot.runaway.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "지진 옥외대피소 정보 DTO")
public class EarthquakePlaceDto {
    private String dongNm;
    private String fcltNm;
    private String addr;
    private String capacity;
    private String wdUtztnHrm;
    private double longitude;
    private double latitude;
    private double distance;

    public EarthquakePlaceDto() {}

    public EarthquakePlaceDto(String dongNm, String fcltNm, String addr, String capacity, String wdUtztnHrm, double longitude, double latitude, double distance) {
        this.dongNm = dongNm;
        this.fcltNm = fcltNm;
        this.addr = addr;
        this.capacity = capacity;
        this.wdUtztnHrm = wdUtztnHrm;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
    }

    public String getDongNm() { return dongNm; }
    public void setDongNm(String dongNm) { this.dongNm = dongNm; }
    public String getFcltNm() { return fcltNm; }
    public void setFcltNm(String fcltNm) { this.fcltNm = fcltNm; }
    public String getAddr() { return addr; }
    public void setAddr(String addr) { this.addr = addr; }
    public String getCapacity() { return capacity; }
    public void setCapacity(String capacity) { this.capacity = capacity; }
    public String getWdUtztnHrm() { return wdUtztnHrm; }
    public void setWdUtztnHrm(String wdUtztnHrm) { this.wdUtztnHrm = wdUtztnHrm; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }
}