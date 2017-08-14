package com.vaadin._depricated_.response.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.domain.Show;
import com.vaadin._depricated_.response.Parser;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Nik on 02.07.2017.
 */
public class ShowParser extends Parser {
    @Override
    protected Collection<?> getEntity(JsonNode root) {
        Collection<Show> shows = new ArrayList<>();

        List<JsonNode> nodes = StreamSupport.stream(Spliterators.spliteratorUnknownSize(root.iterator(),
                Spliterator.ORDERED), false).collect(Collectors.<JsonNode> toList());

        for (JsonNode node : nodes) {
            Show show = new Show(node.get("show_id").asInt(),
                    node.get("film_nameCinema").asText(), node.get("room_id").asInt(),
                    new Date(node.get("show_time").asLong()), node.get("film_name").asText());

            shows.add(show);
        }

        return shows;
    }
}
