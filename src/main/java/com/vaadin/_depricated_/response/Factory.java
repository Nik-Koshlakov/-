package com.vaadin._depricated_.response;

import com.vaadin._depricated_.response.parse.*;

/**
 * Created by Nik on 02.07.2017.
 */
public class Factory {
    public static Parser getParser(String type) {
        Parser parser = null;

        switch(type)
        {
            case "Film" :
                parser = new FilmParser();
                break;
            case "Reserve" :
                parser = new ReserveParser();
                break;
            case "Room" :
                parser = new RoomParser();
                break;
            case "Show" :
                parser = new ShowParser();
                break;
            case "Ticket" :
                parser = new TicketParser();
                break;
        }

        return parser;
    }
}
