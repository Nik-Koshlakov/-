package com.vaadin.domain;

import java.util.Date;

/**
 * Created by Nik on 20.06.2017.
 */
public class Show {
    public static final String CLASS_NAME = "Show";

    private Integer show_id;
    private String Film_nameCinema;
    private Integer room_id;
    private Date show_time;
    private String film_name;

    public Show() {
    }

    public Show(Show show) {
        show_id = show.getShow_id();
        Film_nameCinema = show.getFilm_nameCinema();
        room_id = show.getRoom_id();
        show_time = show.getShow_time();
        film_name = show.getFilm_name();
    }

    public Show(Integer show_id, String film_nameCinema, Integer room_id, Date show_time, String film_name) {
        this.show_id = show_id;
        this.Film_nameCinema = film_nameCinema;
        this.room_id = room_id;
        this.show_time = show_time;
        this.film_name = film_name;
    }

    public Date getShow_time() {
        return show_time;
    }

    public String getFilm_nameCinema() {
        return Film_nameCinema;
    }

    public void setFilm_nameCinema(String film_nameCinema) {
        Film_nameCinema = film_nameCinema;
    }

    public void setShow_time(Date show_time) {
        this.show_time = show_time;
    }

    public String getFilm_name() {
        return film_name;
    }

    public void setFilm_name(String film_name) {
        this.film_name = film_name;
    }

    public Integer getShow_id() {
        return show_id;
    }

    public void setShow_id(Integer show_id) {
        this.show_id = show_id;
    }

    public Integer getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
    }

    @Override
    public String toString() {
        return "ShowParser{" +
                "show_id=" + show_id +
                ", Film_nameCinema='" + Film_nameCinema + '\'' +
                ", room_id=" + room_id +
                ", show_time=" + show_time +
                ", film_name='" + film_name + '\'' +
                '}';
    }
}
