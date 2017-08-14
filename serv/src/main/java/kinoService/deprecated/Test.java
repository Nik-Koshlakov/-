package kinoService.deprecated;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.io.FileUtils;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Nik on 02.04.2017.
 */
//@Component
@Deprecated
public class Test {
    String request = "https://kudago.com/public-api/v1.2/movie-showings/?expand=movie,place&page_size=300&location=msk&lang=en";

    // now each 1 min, 1 hour and etc
    //@Scheduled(cron = "0 0 * * * *")
    public void getData() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(responseEntity.getBody());

            ArrayNode results = (ArrayNode) root.get("results");
            JsonNode film = results.get(0);

            JsonNode movie = film.get("movie");
            JsonNode place = film.get("place");

            JsonNode nameMovie = movie.get("title"); // TODO: not null
            JsonNode posterMovie = movie.get("poster").get("image");

            int id_film = movie.get("id").asInt();
            getDetailsFilm(id_film);
            getImage(restTemplate, posterMovie, nameMovie);

            Long timeBeginningMovie = film.get("datetime").asLong();
            Date dateBeginningmovie = new Date(timeBeginningMovie * 1000); // date of the beginning

            JsonNode nameCinema = place.get("title");
            JsonNode addressCinema = place.get("address");
            JsonNode phone = place.get("phone"); // TODO: not null
            JsonNode stationAboutCinema = place.get("subway");

            boolean imax = film.get("imax").asBoolean();
            String price = film.get("price").asText(); // TODO: not null

            System.out.println(nameCinema);
            System.out.println(dateBeginningmovie);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getDetailsFilm(int id) {
        String request = "https://kudago.com/public-api/v1.3/movies/" + id;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(responseEntity.getBody());

            JsonNode description = root.get("body_text");
            JsonNode running_time = root.get("running_time"); // TODO: not null - продолжительность, в минутах

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getImage(RestTemplate restTemplate, JsonNode imageUrl, JsonNode title) throws IOException {
        File file = new File(new File("").getAbsolutePath() + "\\image\\" + title.textValue()+".jpg");
        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.IMAGE_JPEG);

        String url = imageUrl.textValue();
        url.replace("\"", "");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(acceptableMediaTypes);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<byte[]> result = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);
        FileUtils.writeByteArrayToFile(file, result.getBody());
    }
}
