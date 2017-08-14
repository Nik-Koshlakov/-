package com.vaadin.service.utils;

import com.vaadin.Constants;
import com.vaadin.domain.User;
import com.vaadin.domain.buyPlaces.InformationAboutSession;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

/**
 * Created by Nik on 04.08.2017.
 */
public class Authorization {
    public static boolean saveUser(User user) {
        return transferData(user, Constants.SET_USER);
    }

    public static boolean comparePassword(User user) {
        return transferData(user, Constants.GET_USER);
    }

    private static boolean transferData(User user, String query) {
        String answer = "";
        try {
            SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory();
            s.setReadTimeout(6000); s.setConnectTimeout(1500);

            RestTemplate restTemplate = new RestTemplate(s);
            HttpEntity<User> httpEntity = new HttpEntity<User>(user, new HttpHeaders());

            ResponseEntity<String> response = restTemplate.exchange(
                    query, HttpMethod.POST,
                    httpEntity, new ParameterizedTypeReference<String>() {}
            );

            ClientHttpRequestFactory c = restTemplate.getRequestFactory();

            if (response.getStatusCode() != HttpStatus.OK)
                System.out.println("Server answered wrong or maybe he broke");

            answer = response.getBody();
        } catch (ResourceAccessException exception) {
            System.out.println("Server is`t available");
        }

        return Boolean.valueOf(answer);
    }
}
