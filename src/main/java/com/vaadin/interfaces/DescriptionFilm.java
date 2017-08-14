package com.vaadin.interfaces;

import com.vaadin.domain.Film;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by Nik on 05.07.2017.
 */
@SpringComponent
@UIScope
public class DescriptionFilm {

    public DescriptionFilm() {
    }

    public static VerticalLayout init(Film film) {
        VerticalLayout frame = new VerticalLayout();

        Label caption = new Label();
        Label description = new Label();
        Label producer = new Label();
        Label yearCountryTimeAge = new Label();

        FileResource resource = null;
        if (film.getFilm_image() != null && !film.getFilm_image().isEmpty())
            resource = new FileResource(new File(film.getFilm_image()));
        Image image = new Image("", resource);
        image.setWidth("300px");
        image.setHeight("320px");

        String upperFirstCharOfFilmName = film.getFilm_name().substring(0, 1).toUpperCase()
                + film.getFilm_name().substring(1);
        caption.setValue("«" + upperFirstCharOfFilmName + "»");
        description.setValue(film.getFilm_description());
        producer.setValue("Режиссер: " + film.getFilm_derictor());

        String yearEtc = film.getFilm_year();
        if (film.getFilm_cuntry() != null)
            yearEtc += ", " + film.getFilm_cuntry();
        if (film.getFilm_length() != null)
            yearEtc += ", " + film.getFilm_length() + " мин";
        if (film.getFilm_age() != null && !film.getFilm_age().equals("null"))
            yearEtc += ", " + film.getFilm_age();

        yearCountryTimeAge.setValue(yearEtc);
        caption.setWidth("100%");
        description.setWidth("100%");
        caption.setStyleName("text-center");

        frame.addComponent(image);
        frame.addComponent(caption);
        frame.addComponent(description);
        frame.addComponent(producer);
        frame.addComponent(yearCountryTimeAge);

        if (film.getFilm_trailer() != null && !film.getFilm_trailer().isEmpty()) {
            Link linkToTrailer = new Link("Трейлер \"" + film.getFilm_name() + "\"", new ExternalResource(film.getFilm_trailer()));
            frame.addComponent(linkToTrailer);
        }

        frame.setComponentAlignment(image, Alignment.TOP_CENTER);
        frame.setComponentAlignment(caption, Alignment.TOP_CENTER);

        frame.setWidth("100%");
        frame.setMargin(false);

        return frame;
    }

    @Test
    public void testDescriptionFilm() {
        VerticalLayout actual = init(new Film(1, "valerian", null, 125, 200.0, false, "usa", "2017",
                null, "16+", "Luck Besson", "Sochi", "Abrikosova", "12312312", "makarenko",
                null, null, null));

        assertTrue(actual.getComponentCount() > 0);
    }
}
