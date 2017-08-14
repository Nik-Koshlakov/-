package com.vaadin._depricated_.response;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Collection;

/**
 * Created by Nik on 02.07.2017.
 */
public abstract class Parser {
    public Collection<?> parse(JsonNode root) {
        Collection<?> collection;

        collection = getEntity(root);

        return collection;
    }

    protected abstract Collection<?> getEntity(JsonNode root);
}
