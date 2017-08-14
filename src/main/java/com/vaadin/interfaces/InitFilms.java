package com.vaadin.interfaces;

import com.vaadin.Constants;
import com.vaadin.domain.Film;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.vaadin.service.utils.RequestService.getResponseFilms;
import static org.junit.Assert.*;

/**
 * Created by Nik on 28.06.2017.
 */
@SpringComponent
@UIScope
public class InitFilms {

        /*
        .v-gridlayout-gridexpandratio {
    background: blue; /* Creates a "border" around the grid.
        margin:     10px; /* Empty space around the layout.
    }

/* Add padding through which the background color shows.
    .v-gridlayout-gridexpandratio .v-gridlayout-margin {
        padding: 2px;
    }

/* Add cell borders and make the cell backgrounds white.
 * Warning: This depends heavily on the HTML structure.
    .v-gridlayout-gridexpandratio > div > div > div {
        padding:    2px;   /* Layout background will show through.
        background: white; /* The cells will be colored white.
    }
         */

    private GridLayout grid = null;
    private List<Film> films;

    public InitFilms() {
        films = new ArrayList<>();
    }

    private GridLayout init() {
        if (films.isEmpty())
            films = getResponseFilms();
//        saveTrailers(films);
        grid = new GridLayout(4, films.size());

        for (Film film : films) {
            FileResource resource = null;
            if (film.getFilm_image() != null && !film.getFilm_image().isEmpty())
                resource = new FileResource(new File(film.getFilm_image()));

            Image image = new Image("", resource);
            image.setWidth("85px");
            image.setHeight("95px");
            image.setDescription(film.getFilm_name());

            Label caption = new Label();
            caption.setValue("«" + film.getFilm_name()+ "»");
            caption.setDescription(film.getFilm_name());

            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.addComponent(caption);
            verticalLayout.setWidth("100%");

            int index = films.indexOf(film);
            grid.addComponent(image, 0, index);
            grid.addComponent(verticalLayout, 1, index, 3, index);
        }

        grid.addStyleName("example-gridlayout");
        grid.addStyleName("v-grid-row-focused");
        grid.setStyleName(".v-gridlayout:hover { background: #f3bd48; /* Цвет фона при наведении */}");

        return grid;
    }

    public void setGrid(GridLayout grid) {
        this.grid = grid;
    }
    public GridLayout getGrid() {
        if (grid == null)
            return init();
        return grid;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
    public List<Film> getFilms() {
        return films;
    }

    /*
    Integer film_id, String film_name, String film_image,
                Integer film_length, Double film_price, Boolean film_imax,
                String film_cuntry, String film_year, String film_trailer,
                String film_age, String film_derictor, String film_nameCinema,
                String film_addressCinema, String film_phone, String film_subway, String film_imageCinema,
                Date film_runningTime, String film_description
     */
    @Test
    public void testInitFilms() {
        for (int i = 0; i < 4; i++)
            films.add(new Film(1, "valerian", null, 125, 200.0, false, "usa", "2017",
                null, "16+", "Luck Besson", "Sochi", "Abrikosova", "12312312", "makarenko",
                null, null, null));

        assertTrue(films.size() > 0);
        assertNotNull(init());
        assertTrue(grid.iterator().hasNext());
    }

    //    private void saveTrailers(List<Film> _films) {
//        try {
//            FileUtils.cleanDirectory(new File(DownloadVideo.pathForSave));
//            for (Film film : _films)
//                DownloadVideo.saveVideo(film.getFilm_trailer());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            for (Film film : _films) {
//                File[] matching = new File(DownloadVideo.pathForSave).listFiles(new FilenameFilter() {
//                    @Override
//                    public boolean accept(File dir, String name) {
//                        return name.startsWith(film.getFilm_name()) && name.endsWith("mp4");
//                    }
//                });
//                film.setFilm_image(matching[0].getAbsolutePath());
//            }
//        }
//    }
}
