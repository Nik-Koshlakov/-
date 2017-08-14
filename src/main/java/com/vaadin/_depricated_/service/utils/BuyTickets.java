package com.vaadin._depricated_.service.utils;

import com.vaadin.domain.buyPlaces.InformationAboutSession;
import com.vaadin.domain.drawing.Place;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

import java.util.List;

/**
 * Created by Nik on 02.08.2017.
 */
@Deprecated
public class BuyTickets {
    static String to = "nikitakoshlakov@ya.ru";
    static String from = "nikitakoshlakov@ya.ru";
    static String host = "smtp.gmail.com";
    static String port = "465";
    static String protocol = "smtp";
    static String subject = "This is the Subject Line!";
    static String text = "This is actual message";
    static String userName = "nikitakoshlakov";
    static String pass = "Nizajto";

    static Properties properties = new Properties() {
        {
            put("mail.smtp.host", host);
            put("mail.smtp.port", port);
            put("mail.smtp.ssl.enable", true);
            put("mail.smtp.username", "sobaka864@gmail.com");
            put("mail.smtp.password", "110994kfhf");
        }
    };

    public static void sendEmail(InformationAboutSession session, List<Place> selectedPlaces) {

    }

    public static void sendEmail() {
        try {
            String username = "sobaka864@gmail.com";
            String password = "110994kfhf";

            // configure the connection to the SMTP server
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setJavaMailProperties(properties);
            mailSender.setUsername(username);
            mailSender.setPassword(password);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("sobaka864@gmail.com");
            helper.setTo("nikitakoshlakov@ya.ru");
            helper.setSubject(subject);
            helper.setText(text, true);

            mailSender.send(message);
            System.out.println("ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
