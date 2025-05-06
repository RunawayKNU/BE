package com.springboot.runaway.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "무더위쉼터 정보 DTO")
public class HotPlaceDto {
    private String dongNm;
    private String fcltNm;
    private String addr;
    private String area;
    private String capacity;
    private String equip;
    private String wdUtztnHrm;
    private double longitude;
    private double latitude;
    private double distance;

    public HotPlaceDto() {
    }

    public HotPlaceDto(String dongNm, String fcltNm, String addr, String area, String capacity, String equip, String wdUtztnHrm, double longitude, double latitude, double distance) {
        this.dongNm = dongNm;
        this.fcltNm = fcltNm;
        this.addr = addr;
        this.area = area;
        this.capacity = capacity;
        this.equip = equip;
        this.wdUtztnHrm = wdUtztnHrm;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getEquip() {
        return equip;
    }

    public void setEquip(String equip) {
        this.equip = equip;
    }

    public String getWdUtztnHrm() {
        return wdUtztnHrm;
    }

    public void setWdUtztnHrm(String wdUtztnHrm) {
        this.wdUtztnHrm = wdUtztnHrm;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
