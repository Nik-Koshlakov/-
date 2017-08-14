package com.vaadin.domain.drawing;


import java.awt.*;

/**
 * Created by Nik on 10.07.2017.
 */
public class Place {
    private Point ll = new Point(0,0), ru = new Point(0,0);
    private int id, price, row, place;
    private boolean vip, selected, bought = false;

    public Place() {
    }

    public Place(int id, int price, int row, int place, boolean vip) {
        this.id = id;
        this.price = price;
        this.row = row;
        this.place = place;
        this.vip = vip;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }
    public void setllru(Point p1, Point p2) {
        this.ll = p1;
        this.ru = p2;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isBought() {
        return bought;
    }

    public Point getLl() {
        return ll;
    }

    public void setLl(Point ll) {
        this.ll = ll;
    }

    public Point getRu() {
        return ru;
    }

    public void setRu(Point ru) {
        this.ru = ru;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    @Override
    public String toString() {
        return "Place{" +
                "price=" + price +
                ", row=" + row +
                ", place=" + place +
                ", vip=" + vip +
                ", selected=" + selected +
                ", bought=" + bought +
                '}';
    }
}
