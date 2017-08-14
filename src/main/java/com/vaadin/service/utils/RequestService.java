package com.vaadin.service.utils;

import com.vaadin.Constants;
import com.vaadin.domain.*;
import com.vaadin.domain.buyPlaces.InformationAboutSession;
import com.vaadin.ui.Notification;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

/**
 * Created by Nik on 12.08.2017.
 */
public class RequestService {
    public static List<Film> getResponseFilms() {
        try {
            SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory();
            s.setReadTimeout(6000); s.setConnectTimeout(1500);

            RestTemplate restTemplate = new RestTemplate(s);

            ResponseEntity<List<Film>> responseEntity = restTemplate.exchange(
                    Constants.GET_FILMS,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Film>>() {
                    }
            );

            if (responseEntity.getStatusCode() != HttpStatus.OK)
                System.out.println("Server answered wrong or maybe he broke");

            return responseEntity.getBody();
        } catch (ResourceAccessException exception) {
            System.out.println("Server is`t available");
        }

        return null;
    }

    public static List<Reserve> sellTickets(InformationAboutSession session) {
        try {
            SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory();
            s.setReadTimeout(6000); s.setConnectTimeout(1500);

            RestTemplate restTemplate = new RestTemplate(s);

            HttpEntity<InformationAboutSession> httpEntity =
                    new HttpEntity<InformationAboutSession>(session, new HttpHeaders());

            ResponseEntity<List<Reserve>> response = restTemplate.exchange(
                    Constants.SET_RESERVE,
                    HttpMethod.POST,
                    httpEntity,
                    new ParameterizedTypeReference<List<Reserve>>() {}
            );

            if (response.getStatusCode() != HttpStatus.OK)
                System.out.println("Server answered wrong or maybe he broken");

            return response.getBody();
        } catch (ResourceAccessException exception) {
            System.out.println("Server is`t available");
        }

        return null;
    }

    public static List<Show> getShowsForFilmCinema(String filmName, String cinemaName) {
        try {
            Map<String, String> uriParam = new HashMap<String, String>();
            uriParam.put("filmName", ModifyString.modify(filmName));
            uriParam.put("cinemaName", ModifyString.modify(cinemaName));

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(Constants.GET_SHOWS_FOR_FILMCINEMA);

            SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory();
            s.setReadTimeout(6000); s.setConnectTimeout(1500);

            RestTemplate restTemplate = new RestTemplate(s);
            ResponseEntity<List<Show>> entity = restTemplate.exchange(
                    builder.buildAndExpand(uriParam).toUri().toString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Show>>() {}
            );

            if (entity.getStatusCode() != HttpStatus.OK)
                System.out.println("Server answered wrong or maybe he broken");

            return entity.getBody();
        } catch (ResourceAccessException exception) {
        System.out.println("Server is`t available");
        }

        return null;
    }

    public static List<Film> getCinemasByFilm(String filmName) {
        try {
            Map<String, String> uriParam = new HashMap<String, String>();
            uriParam.put("filmName", ModifyString.modify(filmName));

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(Constants.GET_CINEMAS_BY_FILM);

            SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory();
            s.setReadTimeout(6000); s.setConnectTimeout(1500);

            RestTemplate restTemplate = new RestTemplate(s);
            ResponseEntity<List<Film>> entity = restTemplate.exchange(
                    builder.buildAndExpand(uriParam).toUri().toString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Film>>() {}
            );

            if (entity.getStatusCode() != HttpStatus.OK)
                    System.out.println("Server answered wrong or maybe he broken");

            return entity.getBody();
        } catch (ResourceAccessException exception) {
            System.out.println("Server is`t available");
        }

        return null;
    }

    /**
     * запрос на получение количества рядом и мест в зале
     * @return
     */
    public static Room getMaxPositionOfRoom(int room_id) {
        try {
            Map<String, Integer> uriParam = new HashMap<String, Integer>();
            uriParam.put("room_id", room_id);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(Constants.GET_MAX_POSITION_ROOM);

            SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory();
            s.setReadTimeout(6000); s.setConnectTimeout(1500);

            RestTemplate restTemplate = new RestTemplate(s);
            ResponseEntity<Room> entity = restTemplate.exchange(
                    builder.buildAndExpand(uriParam).toUri().toString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Room>() {}
            );

            if (entity.getStatusCode() != HttpStatus.OK)
                System.out.println("Server answered wrong or maybe he broken");

            return entity.getBody();
        } catch (ResourceAccessException exception) {
            System.out.println("Server is`t available");
        }

        return null;
    }

    /**
     * запрос на получение купленных билетов
     * @return
     */
    public static List<Ticket> getBoughtTickets(int show_id) {
        List<Ticket> tickets = new ArrayList<>();
        try {
            Map<String, Object> uriParam = new HashMap<String, Object>();
            uriParam.put("show_id", show_id);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(Constants.GET_BOUGTH_TICKETS_BY_SHOW_ID);

            SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory();
            s.setReadTimeout(6000); s.setConnectTimeout(1500);

            RestTemplate restTemplate = new RestTemplate(s);
            ResponseEntity<List<Ticket>> entity = restTemplate.exchange(
                    builder.buildAndExpand(uriParam).toUri().toString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Ticket>>() {}
            );

            if (entity.getStatusCode() != HttpStatus.OK)
                System.out.println("Server answered wrong or maybe he broken");

            tickets = entity.getBody();
        } catch (ResourceAccessException exception) {
            System.out.println("Server is`t available");
        }

        if (!tickets.isEmpty())
            tickets.sort((obj1, obj2) ->  {
                int value1 = obj1.getTicket_row().compareTo(obj2.getTicket_row());
                if (value1 == 0)
                    return obj2.getTicket_place().compareTo(obj2.getTicket_place());

                return value1;
            });

        return tickets;
    }
}
