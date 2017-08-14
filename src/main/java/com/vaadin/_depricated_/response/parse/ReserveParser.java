package com.vaadin._depricated_.response.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.domain.Reserve;
import com.vaadin._depricated_.response.Parser;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Nik on 02.07.2017.
 */
public class ReserveParser extends Parser {
    @Override
    protected Collection<?> getEntity(JsonNode root) {
        Collection<Reserve> reserves = new ArrayList<>();

        List<JsonNode> nodes = StreamSupport.stream(Spliterators.spliteratorUnknownSize(root.iterator(),
                Spliterator.ORDERED), false).collect(Collectors.<JsonNode> toList());

        for (JsonNode node : nodes) {
            Reserve reserve = new Reserve(node.get("reserve_id").asInt(),
                    node.get("show_id").asInt(), node.get("ticket_id").asInt(),
                    node.get("visitor_name").asText(), node.get("ticket_code").asText());

            reserves.add(reserve);
        }

        return reserves;
    }
}
