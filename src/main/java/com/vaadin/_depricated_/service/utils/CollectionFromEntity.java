package com.vaadin._depricated_.service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.domain.*;
import com.vaadin._depricated_.response.Factory;
import com.vaadin._depricated_.response.Parser;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Nik on 03.08.2017.
 */
@Deprecated
public class CollectionFromEntity {
    public static Collection<?> getCollection(String className, String bodyEntity) {
        ObjectMapper mapper = new ObjectMapper();
        Parser parser = null;
        try {
            switch (className) {
                case "Film":
                    parser = Factory.getParser(Film.CLASS_NAME);
                    break;
                case "Reserve":
                    parser = Factory.getParser(Reserve.CLASS_NAME);
                    break;
                case "Room":
                    parser = Factory.getParser(Room.CLASS_NAME);
                    break;
                case "Show":
                    parser = Factory.getParser(Show.CLASS_NAME);
                    break;
                case "Ticket":
                    parser = Factory.getParser(Ticket.CLASS_NAME);
                    break;
            }

            return  parser.parse(mapper.readTree(bodyEntity));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
