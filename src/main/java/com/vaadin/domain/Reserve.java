package com.vaadin.domain;

/**
 * Created by Nik on 20.06.2017.
 */
public class Reserve {
    public static final String CLASS_NAME = "Reserve";

    private Integer reserve_id;
    private Integer show_id;
    private Integer ticket_id;
    private String visitor_name;
    private String ticket_code;

    public Reserve() {
    }

    public Reserve(Reserve reserve) {
        reserve_id = reserve.getReserve_id();
        show_id = reserve.getShow_id();
        ticket_id = reserve.getTicket_id();
        visitor_name = reserve.getVisitor_name();
        ticket_code = reserve.getTicket_code();
    }

    public Reserve(Integer reserve_id, Integer show_id, Integer ticket_id, String visitor_name, String ticket_code) {
        this.reserve_id = reserve_id;
        this.show_id = show_id;
        this.ticket_id = ticket_id;
        this.visitor_name = visitor_name;
        this.ticket_code = ticket_code;
    }

    public Integer getReserve_id() {
        return reserve_id;
    }

    public void setReserve_id(Integer reserve_id) {
        this.reserve_id = reserve_id;
    }

    public Integer getShow_id() {
        return show_id;
    }

    public void setShow_id(Integer show_id) {
        this.show_id = show_id;
    }

    public Integer getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(Integer ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getVisitor_name() {
        return visitor_name;
    }

    public void setVisitor_name(String visitor_name) {
        this.visitor_name = visitor_name;
    }

    public String getTicket_code() {
        return ticket_code;
    }

    public void setTicket_code(String ticket_code) {
        this.ticket_code = ticket_code;
    }

    @Override
    public String toString() {
        return "Reserve{" +
                "ticket_id=" + ticket_id +
                ", visitor_name='" + visitor_name + '\'' +
                ", ticket_code='" + ticket_code + '\'' +
                '}';
    }
}
