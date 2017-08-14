package com.vaadin._depricated_.response.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.domain.Ticket;
import com.vaadin._depricated_.response.Parser;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Nik on 02.07.2017.
 */
public class TicketParser extends Parser {
    @Override
    protected Collection<?> getEntity(JsonNode root) {
        Collection<Ticket> tickets = new ArrayList<>();

        List<JsonNode> nodes = StreamSupport.stream(Spliterators.spliteratorUnknownSize(root.iterator(),
                Spliterator.ORDERED), false).collect(Collectors.<JsonNode> toList());

        for (JsonNode node : nodes) {
            Ticket ticket = new Ticket(node.get("ticket_id").asInt(),
                    node.get("room_id").asInt(), node.get("ticket_row").asInt(),
                    node.get("ticket_place").asInt());

            tickets.add(ticket);
        }

        return tickets;
    }
}
