package com.vaadin.domain;

/**
 * Created by Nik on 20.06.2017.
 */
public class Ticket {
    public static final String CLASS_NAME = "Ticket";

    private Integer ticket_id;
    private Integer room_id;
    private Integer ticket_row;
    private Integer ticket_place;

    public Ticket() {
    }

    public Ticket(Ticket ticket) {
        ticket_id = ticket.getTicket_id();
        room_id = ticket.getRoom_id();
        ticket_row = ticket.getTicket_row();
        ticket_place = ticket.getTicket_place();
    }

    public Ticket(Integer ticket_id, Integer room_id, Integer ticket_row, Integer ticket_place) {
        this.ticket_id = ticket_id;
        this.room_id = room_id;
        this.ticket_row = ticket_row;
        this.ticket_place = ticket_place;
    }

    public Integer getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(Integer ticket_id) {
        this.ticket_id = ticket_id;
    }

    public Integer getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
    }

    public Integer getTicket_row() {
        return ticket_row;
    }

    public void setTicket_row(Integer ticket_row) {
        this.ticket_row = ticket_row;
    }

    public Integer getTicket_place() {
        return ticket_place;
    }

    public void setTicket_place(Integer ticket_place) {
        this.ticket_place = ticket_place;
    }

    @Override
    public String toString() {
        return "TicketParser{" +
                "ticket_id=" + ticket_id +
                ", room_id=" + room_id +
                ", ticket_row=" + ticket_row +
                ", ticket_place=" + ticket_place +
                '}';
    }
}
