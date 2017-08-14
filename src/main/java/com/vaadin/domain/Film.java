package com.vaadin.domain;

import java.util.Date;

/**
 * Created by Nik on 20.06.2017.
 */
public class Film {
    public static final String CLASS_NAME = "Film";

    private Integer Film_id;
    private String Film_name;
    private String Film_image;
    private Integer Film_length;
    private Double Film_price;
    private Boolean Film_imax;
    private String Film_cuntry;
    private String Film_year;
    private String Film_trailer;
    private String Film_age;
    private String Film_derictor;
    private String Film_nameCinema;
    private String Film_addressCinema;
    private String Film_phone;
    private String Film_subway;
    private String Film_imageCinema;
    private String Film_description;

    private Date Film_runningTime;

    public Film() {
    }

    public Film(Film film) {
        Film_id = film.getFilm_id();
        Film_name = film.getFilm_name();
        Film_image = film.getFilm_image();
        Film_length = film.getFilm_length();
        Film_price = film.getFilm_price();
        Film_imax = film.getFilm_imax();
        Film_cuntry = film.getFilm_cuntry();
        Film_year = film.getFilm_year();
        Film_trailer = film.getFilm_trailer();
        Film_age = film.getFilm_age();
        Film_derictor = film.getFilm_derictor();
        Film_nameCinema = film.getFilm_nameCinema();
        Film_addressCinema = film.getFilm_addressCinema();
        Film_phone = film.getFilm_phone();
        Film_subway = film.getFilm_subway();
        Film_imageCinema = film.getFilm_imageCinema();
        Film_description = film.getFilm_description();

        Film_runningTime = film.getFilm_runningTime();
    }

    public Film(Integer film_id, String film_name, String film_image,
                Integer film_length, Double film_price, Boolean film_imax,
                String film_cuntry, String film_year, String film_trailer,
                String film_age, String film_derictor, String film_nameCinema,
                String film_addressCinema, String film_phone, String film_subway, String film_imageCinema,
                Date film_runningTime, String film_description) {
        Film_id = film_id;
        Film_name = film_name;
        Film_image = film_image;
        Film_length = film_length;
        Film_price = film_price;
        Film_imax = film_imax;
        Film_cuntry = film_cuntry;
        Film_year = film_year;
        Film_trailer = film_trailer;
        Film_age = film_age;
        Film_derictor = film_derictor;
        Film_nameCinema = film_nameCinema;
        Film_addressCinema = film_addressCinema;
        Film_phone = film_phone;
        Film_subway = film_subway;
        Film_imageCinema = film_imageCinema;
        Film_runningTime = film_runningTime;
        Film_description = film_description;
    }

    public String getFilm_description() {
        return Film_description;
    }

    public void setFilm_description(String film_description) {
        Film_description = film_description;
    }

    public Date getFilm_runningTime() {
        return Film_runningTime;
    }

    public void setFilm_runningTime(Date film_runningTime) {
        Film_runningTime = film_runningTime;
    }

    public String getFilm_imageCinema() {
        return Film_imageCinema;
    }

    public void setFilm_imageCinema(String film_imageCinema) {
        Film_imageCinema = film_imageCinema;
    }

    public String getFilm_nameCinema() {
        return Film_nameCinema;
    }

    public void setFilm_nameCinema(String film_nameCinema) {
        Film_nameCinema = film_nameCinema;
    }

    public String getFilm_addressCinema() {
        return Film_addressCinema;
    }

    public void setFilm_addressCinema(String film_addressCinema) {
        Film_addressCinema = film_addressCinema;
    }

    public String getFilm_phone() {
        return Film_phone;
    }

    public void setFilm_phone(String film_phone) {
        Film_phone = film_phone;
    }

    public String getFilm_subway() {
        return Film_subway;
    }

    public void setFilm_subway(String film_subway) {
        Film_subway = film_subway;
    }

    public String getFilm_cuntry() {
        return Film_cuntry;
    }

    public void setFilm_cuntry(String film_cuntry) {
        Film_cuntry = film_cuntry;
    }

    public String getFilm_year() {
        return Film_year;
    }

    public void setFilm_year(String film_year) {
        Film_year = film_year;
    }

    public String getFilm_trailer() {
        return Film_trailer;
    }

    public void setFilm_trailer(String film_trailer) {
        Film_trailer = film_trailer;
    }

    public String getFilm_age() {
        return Film_age;
    }

    public void setFilm_age(String film_age) {
        Film_age = film_age;
    }

    public String getFilm_derictor() {
        return Film_derictor;
    }

    public void setFilm_derictor(String film_derictor) {
        Film_derictor = film_derictor;
    }

    public Integer getFilm_id() {
        return Film_id;
    }

    public void setFilm_id(Integer film_id) {
        Film_id = film_id;
    }

    public String getFilm_name() {
        return Film_name;
    }

    public void setFilm_name(String film_name) {
        Film_name = film_name;
    }

    public String getFilm_image() {
        return Film_image;
    }

    public void setFilm_image(String film_image) {
        Film_image = film_image;
    }

    public Integer getFilm_length() {
        return Film_length;
    }

    public void setFilm_length(Integer film_length) {
        Film_length = film_length;
    }

    public Double getFilm_price() {
        return Film_price;
    }

    public void setFilm_price(Double film_price) {
        Film_price = film_price;
    }

    public Boolean getFilm_imax() {
        return Film_imax;
    }

    public void setFilm_imax(Boolean film_imax) {
        Film_imax = film_imax;
    }

    @Override
    public String toString() {
        return "Film{" +
                "Film_id=" + Film_id +
                ", Film_name='" + Film_name + '\'' +
                ", Film_image='" + Film_image + '\'' +
                ", Film_length=" + Film_length +
                ", Film_price=" + Film_price +
                ", Film_imax=" + Film_imax +
                ", Film_cuntry='" + Film_cuntry + '\'' +
                ", Film_year='" + Film_year + '\'' +
                ", Film_trailer='" + Film_trailer + '\'' +
                ", Film_age='" + Film_age + '\'' +
                ", Film_derictor='" + Film_derictor + '\'' +
                ", Film_nameCinema='" + Film_nameCinema + '\'' +
                ", Film_addressCinema='" + Film_addressCinema + '\'' +
                ", Film_phone='" + Film_phone + '\'' +
                ", Film_subway='" + Film_subway + '\'' +
                '}';
    }
}
