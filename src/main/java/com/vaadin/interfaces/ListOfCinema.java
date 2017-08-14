package com.vaadin.interfaces;

import com.vaadin.Constants;
import com.vaadin.domain.Film;
import com.vaadin.domain.buyPlaces.InformationAboutSession;
import com.vaadin.domain.Show;
import com.vaadin.server.FileResource;
import com.vaadin.service.utils.ModifyString;
import com.vaadin.service.utils.RequestService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Nik on 28.06.2017.
 */
@SpringComponent
@UIScope
public class ListOfCinema {

    private Button.ClickListener clickListener;
    private InformationAboutSession aboutSession;

    public ListOfCinema() { }

    private Map<String, List<Show>> fillSeanses(String filmName, List<Film> cinemas) {
        Map<String, List<Show>> result = new HashMap<>();

        for (Film cinema : cinemas) {
            String cinema_name = cinema.getFilm_nameCinema();
            List<Show> shows = RequestService.getShowsForFilmCinema(filmName, cinema_name);
            result.put(cinema_name, shows);
        }

        return result;
    }

    public GridLayout init(InformationAboutSession session) {
        this.aboutSession = session;

        String film_name = aboutSession.getFilm_name();
        List<Film> cinemas = RequestService.getCinemasByFilm(film_name);
        Map<String, List<Show>> seanses = fillSeanses(film_name, cinemas);

        GridLayout grid = new GridLayout(4, cinemas.size());
        grid.addStyleName("example-gridlayout");
        grid.addStyleName("v-grid-row-focused");

        for (Film cinema : cinemas) {
            FileResource resource = null;
            if (cinema.getFilm_imageCinema() != null && !cinema.getFilm_imageCinema().isEmpty())
                resource = new FileResource(new File(cinema.getFilm_imageCinema()));

            Image image = new Image("", resource);
            image.setWidth("85px");
            image.setHeight("100px");
            image.setDescription(cinema.getFilm_nameCinema());

            VerticalLayout verticalLayout = new VerticalLayout();
            Label caption = new Label();
            Label placeOfCinema = new Label();

            String upperFirstCharOfNameCinema = cinema.getFilm_nameCinema().substring(0, 1).toUpperCase()+ cinema.getFilm_nameCinema().substring(1);
            caption.setValue(upperFirstCharOfNameCinema);

            placeOfCinema.setValue(cinema.getFilm_addressCinema() + ", м. " + cinema.getFilm_subway());
            placeOfCinema.setHeight("30px");

            verticalLayout.addComponent(caption);
            verticalLayout.addComponent(placeOfCinema);
            verticalLayout.setSpacing(false);

            List<Show> shows = seanses.get(cinema.getFilm_nameCinema());
            int index = cinemas.indexOf(cinema);

            fillButtons(verticalLayout, shows, index);
            grid.addComponent(image, 0, index);
            grid.addComponent(verticalLayout, 1, index, 3, index);
        }
        grid.setStyleName(".v-gridlayout:hover { background: #f3bd48; /* Цвет фона при наведении */}");

        grid.setMargin(false);

        return grid;
    }

    private void fillButtons(VerticalLayout verticalLayout, List<Show> shows, int index) {
        HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        HorizontalLayout horizontalLayout2 = new HorizontalLayout();

        horizontalLayout1.setSizeFull();
        horizontalLayout2.setSizeFull();
        for (int i = 0; i < shows.size(); i++) {
            Show sh = shows.get(i);
            Button button = new Button();
            button.setId(sh.getShow_id() + " " + sh.getFilm_nameCinema());
            button.setDescription(sh.getRoom_id() + " " + sh.getShow_time());

            DateFormat sdf = new SimpleDateFormat("HH:MM:ss", Locale.UK);
            button.setCaption(sdf.format(shows.get(i).getShow_time()));
            button.setWidth("100px");
            button.setHeight("30px");
            button.addClickListener(clickListener);

            if (i <= 4) {
                horizontalLayout1.addComponent(button);
            } else {
                horizontalLayout2.addComponent(button);
            }
        }

        verticalLayout.addComponent(horizontalLayout1);
        if (horizontalLayout2.getComponentCount() > 0)
            verticalLayout.addComponent(horizontalLayout2);
    }

    public InformationAboutSession getAboutSession() {
        return aboutSession;
    }

    public void setAboutSession(InformationAboutSession aboutSession) {
        this.aboutSession = aboutSession;
    }

    public void setClickListener(Button.ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
