package com.vaadin.domain;

/**
 * Created by Nik on 20.06.2017.
 */
public class Room {
    public static final String CLASS_NAME = "Room";

    private Integer room_id;
    private Double room_vip_coef;
    private Double room_not_vip_coef;
    private Integer max_row;
    private Integer max_place;

    public Room() {
    }

    public Room(Room room) {
        room_id = room.getRoom_id();
        room_vip_coef = room.getRoom_vip_coef();
        room_not_vip_coef = room.getRoom_not_vip_coef();
        max_row = room.getMax_row();
        max_place = room.getMax_place();
    }

    public Room(Integer room_id, Double room_vip_coef, Double room_not_vip_coef,
                Integer max_row, Integer max_place) {
        this.room_id = room_id;
        this.room_vip_coef = room_vip_coef;
        this.room_not_vip_coef = room_not_vip_coef;
        this.max_row = max_row;
        this.max_place = max_place;
    }

    public Integer getMax_row() {
        return max_row;
    }

    public void setMax_row(Integer max_row) {
        this.max_row = max_row;
    }

    public Integer getMax_place() {
        return max_place;
    }

    public void setMax_place(Integer max_place) {
        this.max_place = max_place;
    }

    public Integer getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
    }

    public Double getRoom_vip_coef() {
        return room_vip_coef;
    }

    public void setRoom_vip_coef(Double room_vip_coef) {
        this.room_vip_coef = room_vip_coef;
    }

    public Double getRoom_not_vip_coef() {
        return room_not_vip_coef;
    }

    public void setRoom_not_vip_coef(Double room_not_vip_coef) {
        this.room_not_vip_coef = room_not_vip_coef;
    }


}
