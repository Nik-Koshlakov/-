package com.vaadin._depricated_.response.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.domain.Film;
import com.vaadin._depricated_.response.Parser;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Nik on 02.07.2017.
 */
public class FilmParser extends Parser {
    @Override
    protected Collection<?> getEntity(JsonNode root) {
        Collection<Film> films = new ArrayList<>();

        List<JsonNode> nodes = StreamSupport.stream(Spliterators.spliteratorUnknownSize(root.iterator(),
                Spliterator.ORDERED), false).collect(Collectors.<JsonNode> toList());

        for (JsonNode node : nodes) {
            Film film = new Film(node.get("film_id").asInt(), node.get("film_name").asText(),
                    node.get("film_image").asText(), node.get("film_length").asInt(),
                    node.get("film_price").asDouble(), node.get("film_imax").asBoolean(),
                    node.get("film_cuntry").asText(), node.get("film_year").asText(),
                    node.get("film_trailer").asText(), node.get("film_age").asText(),
                    node.get("film_derictor").asText(), node.get("film_nameCinema").asText(),
                    node.get("film_addressCinema").asText(), node.get("film_phone").asText(),
                    node.get("film_subway").asText(), node.get("film_imageCinema").asText(),
                    new Date(node.get("film_runningTime").asLong()), node.get("film_description").asText());

            films.add(film);
        }

        return films;
    }
}
