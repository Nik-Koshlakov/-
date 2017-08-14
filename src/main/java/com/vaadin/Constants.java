package com.vaadin;

/**
 * Created by Nik on 28.06.2017.
 */
public interface Constants {
    public static final String SET_RESERVE = "http://localhost:8085/db/setReserve";
    public static final String GET_FILMS = "http://localhost:8085/db/getFilms";
    public static final String SET_USER = "http://localhost:8085/db/saveUser";
    public static final String GET_USER = "http://localhost:8085/db/getUser";
    public static final String GET_CINEMAS_BY_FILM = "http://localhost:8085/db/getCinemasByFilm/{filmName}";
    public static final String GET_SHOWS_FOR_FILMCINEMA = "http://localhost:8085/db/getShowsForFilmCinema/{filmName}/{cinemaName}";
    public static final String GET_BOUGTH_TICKETS = "http://localhost:8085/db/getBougthTickets/{filmName}/{cinemaName}/{room_id}";
    public static final String GET_BOUGTH_TICKETS_BY_SHOW_ID = "http://localhost:8085/db/getBougthTickets/{show_id}";
    public static final String GET_MAX_POSITION_ROOM = "http://localhost:8085/db/getMaxPositionRoom/{room_id}";
}
