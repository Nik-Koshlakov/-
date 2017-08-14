package kinoService.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import kinoService.domain.Movie;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

//import org.junit.*;
//
//import static org.junit.Assert.*;

/**
 * Created by Nik on 19.06.2017.
 */
public class FilmsServiceImpl {
    private static final String GET_FILMS = "https://kudago.com/public-api/v1.2/movie-showings/?expand=movie,place&page_size=400&location=msk&lang=en";
    private static final String GET_DETAILS_ABOUT_MOVIE = "https://kudago.com/public-api/v1.3/movies/";

    public FilmsServiceImpl() {
    }

    private static List<Movie> list = new ArrayList<Movie>();
    public static Collection<Movie> getData() {
        if (!list.isEmpty())
            return list;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(GET_FILMS, String.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(responseEntity.getBody());

            ArrayNode results = (ArrayNode) root.get("results");
            int n = 0;
            for (JsonNode film : results) {
                n++;
                Movie entity = new Movie();

                JsonNode movie = film.get("movie");
                JsonNode place = film.get("place");

                String nameMovie = movie.get("title").asText(); // TODO: not null
                String posterMovie = movie.get("poster").get("image").asText();

                int id_film = movie.get("id").asInt();
                filmDetails(entity, id_film);
                saveMovieImage(entity, posterMovie, nameMovie);

                Long timeBeginningMovie = film.get("datetime").asLong();
                Date dateBeginningmovie = new Date(timeBeginningMovie * 1000); // date of the beginning

                String nameCinema = place.get("title").asText();
                ArrayNode imagesCinema = (ArrayNode) place.get("images");
                String imageCinema = ((JsonNode)imagesCinema.elements().next()).get("image").asText();
                saveCinemaImage(entity, imageCinema, nameCinema);

                String addressCinema = place.get("address").asText();
                String phone = place.get("phone").asText(); // TODO: not null
                String stationAboutCinema = place.get("subway").asText();
                boolean imax = film.get("imax").asBoolean();

                String priceStr = film.get("price").asText(); // TODO: not null

                if (dateBeginningmovie != null && nameMovie != null && phone != null
                        && priceStr != null && !priceStr.equals("null")) {
                    entity.setBeginning_time(dateBeginningmovie);
                    entity.setNameMovie(nameMovie);
                    entity.setPhone(phone);

                    String rubls = priceStr.substring(0, priceStr.indexOf(" "));

                    entity.setPrice(concatCost(rubls));
                    entity.setNameCinema(nameCinema);
                    entity.setAddressCinema(addressCinema);
                    entity.setStationAboutCinema(stationAboutCinema);
                    entity.setImax(imax);

                    list.add(entity);
                    System.out.println("count: " + n + "; id film: " + id_film + "; countOfList: " + list.size());
                } else {
                    System.out.println("count: " + n + "; price is null");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return list;
        }
    }

    private static void filmDetails(Movie movie, int id) throws IOException{
        String request = GET_DETAILS_ABOUT_MOVIE + id;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseEntity.getBody());

        JsonNode country = root.get("country");
        JsonNode year = root.get("year");
        JsonNode trailer = root.get("trailer");
        JsonNode age = root.get("age_restriction");
        JsonNode director = root.get("director");

        JsonNode description = root.get("body_text");
        JsonNode running_time = root.get("running_time"); // TODO: not null - продолжительность, в минутах

        movie.setDescription(concatDescription(description.asText()));
        movie.setRunning_time(running_time.asInt());
        movie.setCountryFilm(country.asText());
        movie.setYearFilm(year.asText());
        movie.setTrailerFilm(trailer.asText());
        movie.setAgeFilm(age.asText());
        movie.setDirectorFilm(director.asText());
    }

    private static String concatDescription(String desc) {
        String result = "";
        if (desc.contains("<p>"))
            result = desc.substring(desc.indexOf(">") + 1, desc.length());

        if (result.contains("</p>"))
            result = result.substring(0, result.indexOf("<"));

        return result;
    }

    private static double concatCost(String rubls) {
        double sum = 0.0;
        if (rubls.contains("–")) {
            String[] prices = rubls.split("–");

            double leftPrice = Double.parseDouble(prices[0]);
            double rightPrice = Double.parseDouble(prices[1]);

            sum = rightPrice - leftPrice;
        } else if (rubls.contains("-")) {
            String[] prices = rubls.split("-");

            double leftPrice = 0;
            if (prices[0].length() > 0)
                leftPrice = Double.parseDouble(prices[0]);
            double rightPrice = Double.parseDouble(prices[1]);

            sum = rightPrice - leftPrice;
        } else {
            if (rubls.contains(",")) {
                int numDelimitChar = rubls.indexOf(",");

                String firstPart = rubls.substring(0, numDelimitChar);
                String secondPart = rubls.substring(numDelimitChar + 1, rubls.length());

                rubls = firstPart + "." + secondPart;
            }
            sum = Double.parseDouble(rubls);
        }
        return sum;
    }

    private static void saveCinemaImage(Movie movie, String imageUrl, String title) throws IOException {
        if (title.contains("\"")) {
            int first_flow = title.indexOf("\"");
            int second_flow = title.lastIndexOf("\"");
            title = title.substring(0, first_flow) + title.substring(first_flow + 1, second_flow) + title.substring(second_flow + 1, title.length());
        }
        String path = new File("").getAbsolutePath() + "\\cinema\\" + title + ".jpg";
        if ((new File(path)).exists()) {
            movie.setPosterCinemaPath(path);
            return;
        }

        File file = new File(path);
        ResponseEntity<byte[]> result = getResult(imageUrl);
        FileUtils.writeByteArrayToFile(file, result.getBody());

        movie.setPosterCinemaPath(file.getAbsolutePath());
    }

    private static void saveMovieImage(Movie movie, String imageUrl, String title) throws IOException {
        if (title.contains(":"))
            title = title.substring(0, title.indexOf(":"));
        String path = new File("").getAbsolutePath() + "\\image\\" + title + ".jpg";
        if ((new File(path)).exists()) {
            movie.setPosterMoviePath(path);
            return;
        }

        File file = new File(path);
        ResponseEntity<byte[]> result = getResult(imageUrl);
        FileUtils.writeByteArrayToFile(file, result.getBody());

        movie.setPosterMoviePath(file.getAbsolutePath());
    }

    private static ResponseEntity<byte[]> getResult(String imageUrl) {
        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.IMAGE_JPEG);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(acceptableMediaTypes);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        ResponseEntity<byte[]> result = restTemplate.exchange(imageUrl, HttpMethod.GET, entity, byte[].class);

        return  result;
    }

    @Test
    public void testConcatDescription() {
        String actual1 = "<p>aaaaa";
        String actual2 = "<p>aaaaa</p>";
        String actual3 = "<p>aaaaa</p>";
        String expect1 = "aaaaa";
        assertEquals("at the same", expect1, concatDescription(actual1));
        assertEquals("at the same", expect1, concatDescription(actual2));
        assertEquals("at the same", expect1, concatDescription(actual3));

        String str = "aaaa<p>";
        assertNotNull(concatDescription(str));

        assertTrue(expect1.length() == concatDescription(actual1).length());
    }

    @Test
    public void testConcatCost() {
        String actual1 = "-120";
        String actual2 = "50-120";
        String actual3 = "500,5";
        String actual4 = "500.5";
        String actual5 = "120";

        double expect0 = -120;
        double expect1 = 120;
        double expect2 = 70;
        double expect3 = 500.5;

        assertNotNull(concatCost(actual1));

        assertEquals(expect0, concatCost(actual1), expect1*2);
        assertEquals(expect1, concatCost(actual5), 0);
        assertEquals(expect2, concatCost(actual2), 0);
        assertEquals(expect3, concatCost(actual3), 0);
        assertEquals(expect3, concatCost(actual4), 0);
        //assertEquals(expect0, concatCost(actual1), 0);
    }
}
