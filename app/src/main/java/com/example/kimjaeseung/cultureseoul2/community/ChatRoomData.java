package com.example.kimjaeseung.cultureseoul2.community;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by kimjaeseung on 2017. 7. 23..
 */

public class ChatRoomData implements Serializable{
    private String firebaseKey;
    private int performanceImage;
    private String roomName;
    private String roomLocation;
    private String roomTime;
    private String roomState;
    private String roomPeople;


    public int getPerformanceImage() {
        return performanceImage;
    }

    public void setPerformanceImage(int performanceImage) {
        this.performanceImage = performanceImage;
    }

    public String getRoomLocation() {
        return roomLocation;
    }

    public void setRoomLocation(String roomLocation) {
        this.roomLocation = roomLocation;
    }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomPeople() {
        return roomPeople;
    }

    public void setRoomPeople(String roomPeople) {
        this.roomPeople = roomPeople;
    }

    public String getRoomState() {
        return roomState;
    }

    public void setRoomState(String roomState) {
        this.roomState = roomState;
    }

    public String getRoomTime() {
        return roomTime;
    }

    public void setRoomTime(String roomTime) {
        this.roomTime = roomTime;
    }
}
