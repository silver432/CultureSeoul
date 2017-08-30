package com.example.kimjaeseung.cultureseoul2.community;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by kimjaeseung on 2017. 7. 23..
 */

public class ChatRoomData implements Serializable {
    private String firebaseKey;
    private String performanceImage;
    private String performanceName;
    private String roomName;
    private String roomLocation;
    private String roomLocationName;
    private String roomTime;
    private String roomDay;
    private int roomPeople;
    private int roomMaxPeople;

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public String getPerformanceImage() {
        return performanceImage;
    }

    public void setPerformanceImage(String performanceImage) {
        this.performanceImage = performanceImage;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomLocation() {
        return roomLocation;
    }

    public void setRoomLocation(String roomLocation) {
        this.roomLocation = roomLocation;
    }

    public String getRoomTime() {
        return roomTime;
    }

    public void setRoomTime(String roomTime) {
        this.roomTime = roomTime;
    }

    public String getRoomDay() {
        return roomDay;
    }

    public void setRoomDay(String roomDay) {
        this.roomDay = roomDay;
    }

    public String getPerformanceName() {
        return performanceName;
    }

    public void setRoomPeople(int roomPeople) {
        this.roomPeople = roomPeople;
    }

    public int getRoomMaxPeople() {
        return roomMaxPeople;
    }

    public void setRoomMaxPeople(int roomMaxPeople) {
        this.roomMaxPeople = roomMaxPeople;
    }

    public void setPerformanceName(String performanceName) {
        this.performanceName = performanceName;

    }

    public String getRoomLocationName() {
        return roomLocationName;
    }

    public void setRoomLocationName(String roomLocationName) {
        this.roomLocationName = roomLocationName;
    }

    public int getRoomPeople() {
        return roomPeople;
    }
}
