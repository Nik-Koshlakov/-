package com.vaadin._depricated_.response.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.domain.Room;
import com.vaadin._depricated_.response.Parser;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Nik on 02.07.2017.
 */
public class RoomParser extends Parser {
    @Override
    protected Collection<?> getEntity(JsonNode root) {
        Collection<Room> rooms = new ArrayList<>();

        List<JsonNode> nodes = StreamSupport.stream(Spliterators.spliteratorUnknownSize(root.iterator(),
                Spliterator.ORDERED), false).collect(Collectors.<JsonNode> toList());

        Room room = new Room(root.get("room_id").asInt(),
                root.get("room_vip_coef").asDouble(), root.get("room_not_vip_coef").asDouble(),
                root.get("max_row").asInt(), root.get("max_place").asInt());

        rooms.add(room);

        return rooms;
    }
}
