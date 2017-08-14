package kinoService.web;

import kinoService.domain.Movie;
import kinoService.service.FilmsServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by Nik on 18.06.2017.
 */
@RestController
@RequestMapping("/")
public class Controller {

    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public Collection<Movie> getFilm() {
        return FilmsServiceImpl.getData();
    }
}
