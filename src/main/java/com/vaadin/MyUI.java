package com.vaadin;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import com.github.javafaker.Faker;
import com.vaadin.annotations.Theme;
import com.vaadin.domain.Film;
import com.vaadin.domain.User;
import com.vaadin.domain.buyPlaces.InformationAboutSession;
import com.vaadin.domain.Reserve;
import com.vaadin.domain.Show;
import com.vaadin.domain.drawing.Place;
import com.vaadin.interfaces.DescriptionFilm;
import com.vaadin.interfaces.DrawingRoom;
import com.vaadin.interfaces.InitFilms;
import com.vaadin.interfaces.ListOfCinema;
import com.vaadin.server.*;
import com.vaadin.service.utils.Authorization;
import com.vaadin.service.utils.ModifyString;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;
import org.vaadin.hezamu.canvas.Canvas;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vaadin.service.utils.RequestService.*;


/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */

@Theme("mytheme")
@SpringUI
public class MyUI extends UI {

    private Canvas canvas;
    @Autowired
    private InitFilms initFilms;
    @Autowired
    private ListOfCinema listOfCinema;
    @Autowired
    private InformationAboutSession session;
    @Autowired
    private DrawingRoom drawingRoom;

    private final Show showForDrawing = new Show();
    private boolean authorization = false;

    private VerticalLayout frame = new VerticalLayout();
    private HorizontalLayout topLayout = new HorizontalLayout();

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public MyUI() {
        if (canvas == null)
            canvas = new Canvas();

        canvas.addContextClickListener(contextClickEvent ->  {
            drawingRoom.fillSelectedPlace(canvas,
                    new Point(contextClickEvent.getRelativeX(), contextClickEvent.getClientY()));
            session.setShow_id(drawingRoom.getShow().getShow_id());
        });
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        if (authorization)
            initFilmsWithAuthorization();
        else
            initFilmsWithoutAuthorization();

        frame.setWidth("100%");
        setContent(frame);

        System.out.println("need size 502:502 or full screen");
        System.out.println("width: " + getPage().getBrowserWindowHeight() + "; height: " + getPage().getBrowserWindowHeight());
    }

    private void initFilmsWithoutAuthorization() {
        initTopLayout("filmsWithoutAuthorization");
        initFilms();
    }

    private void initFilmsWithAuthorization() {
        initTopLayout("filmsWithAuthorization");
        initFilms();
    }

    private void initFilms() {
        GridLayout grid = initFilms.getGrid();

        frame.addComponent(grid);
        frame.setComponentAlignment(grid, Alignment.TOP_LEFT);

        grid.addLayoutClickListener(click -> {
            Component component = click.getChildComponent();
            String nameFilm = null;
            if (component instanceof Image) {
                nameFilm = component.getDescription();
            } else {
                Component captionLabel = ((VerticalLayout)component).getComponent(0);
                nameFilm = captionLabel.getDescription();
            }
            //System.out.println(nameFilm);
            initDescriptionFilm(searchFilmByName(nameFilm));
        });
    }

    private void initDescriptionFilm(final Film film) {
        initTopLayout("informationAboutFilm");

        VerticalLayout informationAboutFilm = DescriptionFilm.init(film);
        Button timeTableAndTickets = new Button("Расписание и билеты");

        frame.addComponent(informationAboutFilm);
        frame.addComponent(timeTableAndTickets);
        frame.setComponentAlignment(timeTableAndTickets, Alignment.TOP_CENTER);

        timeTableAndTickets.addClickListener(clickEvent -> initCinemasForFilm(film));
    }

    private void initCinemasForFilm(Film film) {
        initTopLayout("cinemasForFilm");
        session.setFilm_name(film.getFilm_name());

        listOfCinema.setClickListener(clickEvent -> {
            Button b = clickEvent.getButton();

            String[] parseIdButton = ModifyString.parseButtonSecretMessage(b.getId());
            showForDrawing.setShow_id(Integer.parseInt(parseIdButton[0]));
            showForDrawing.setFilm_nameCinema(parseIdButton[1]);
            showForDrawing.setFilm_name(session.getFilm_name());

            String[] parseDescrButton = ModifyString.parseButtonSecretMessage(b.getDescription());
            showForDrawing.setRoom_id(Integer.parseInt(parseDescrButton[0]));

            session.setFilm_nameCinema(parseIdButton[1]);
            session.setRoom_id(showForDrawing.getRoom_id());

            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
                showForDrawing.setShow_time(simpleDateFormat.parse(parseDescrButton[1]));
                session.setShow_time(showForDrawing.getShow_time());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            initDrawRoom(film, showForDrawing);
        });

        GridLayout grid = listOfCinema.init(session);

        frame.addComponent(grid);
        frame.setComponentAlignment(grid, Alignment.TOP_LEFT);
    }

    private void redraw() {
        drawingRoom.setSize(getPage().getBrowserWindowWidth(), getPage().getBrowserWindowHeight());
        drawingRoom.draw(canvas);
    }

    private void initDrawRoom(Film film, Show show) {
        initTopLayout("drawRoom");

        drawingRoom.setSize(getPage().getBrowserWindowWidth(), getPage().getBrowserWindowHeight());
        drawingRoom.setShow(show); drawingRoom.setFilm(film);
        drawingRoom.draw(canvas);

        canvas.setWidth("100%");
        frame.addComponent(canvas);
    }

    private void initTopLayout(String page) {
        topLayout.removeAllComponents();
        frame.removeAllComponents();

        switch(page) {
            case "filmsWithAuthorization":
                Label nameUser = new Label();
                Button logout = new Button("Выйти");

                nameUser.setValue(session.getVisitorName());
                logout.addClickListener(clickEvent -> {
                    initFilmsWithoutAuthorization();
                    session.setVisitorName("");
                    session.setVisitorEmail("");
                });

                topLayout.addComponent(nameUser);
                topLayout.addComponent(logout);
                topLayout.setComponentAlignment(nameUser, Alignment.TOP_LEFT);
                topLayout.setComponentAlignment(logout, Alignment.TOP_RIGHT);

                break;
            case "filmsWithoutAuthorization":
                VerticalLayout verticalLayout = new VerticalLayout();
                HorizontalLayout forButtons = new HorizontalLayout();

                PopupView popup = new PopupView("Авторизация", verticalLayout);
                TextField email = new TextField();
                TextField password = new TextField();

                email.setPlaceholder("input email");
                password.setPlaceholder("input password");

                verticalLayout.addComponent(email);
                verticalLayout.addComponent(password);
                verticalLayout.addComponent(forButtons);

                Button enter = new Button("войти");
                enter.addClickListener(clickEvent -> {
                    enter.setComponentError(null);
                    password.setComponentError(null);

                    String emailUser = email.getValue();
                    if (!emailUser.isEmpty() && validate(emailUser)) {
                        if (password.getValue().isEmpty()) {
                            password.setComponentError(new UserError("Введите пароль"));
                            setAuthorization(false);
                        }
                        else {
                            User u = new User(emailUser.substring(0, emailUser.indexOf("@")), emailUser, password.getValue());
                            Boolean response = Authorization.comparePassword(u);
                            if (response) {
                                setAuthorization(true);
                                session.setVisitorName(u.getName());
                                session.setVisitorEmail(u.getEmail());
                                initFilmsWithAuthorization();
                            } else {
                                setAuthorization(false);
                                enter.setComponentError(new UserError("Неправильный email или пароль \n или Вы не зарегистрированы"));
                            }
                        }
                    }

                    popup.setPopupVisible(true);
                });

                Button authorization = new Button("зарегистрироваться");
                authorization.addClickListener(clickEvent ->  {
                    authorization.setComponentError(null);

                    String em = email.getValue();
                    if (!em.isEmpty() && validate(em)) {

                        if (password.getValue().isEmpty())
                            authorization.setComponentError(new UserError("Введите пароль"));

                        User u = new User(em.substring(0, em.indexOf("@")), em, password.getValue());
                        Boolean response = Authorization.saveUser(u);
                        if (response) {
                            Notification.show("Вы успешно зарегистрированы");
                            session.setVisitorName(u.getName());
                            session.setVisitorEmail(u.getEmail());
                            setAuthorization(true);
                            initFilmsWithAuthorization();
                        } else {
                            setAuthorization(false);
                            Notification.show("Что-то пошло не так :)");
                        }
                    } else {
                        setAuthorization(false);
                        authorization.setComponentError(new UserError("Неверый формат email"));
                    }

                    popup.setPopupVisible(true);
                });

                Button cancel = new Button("отменить");
                cancel.addClickListener(clickEvent ->  {
                    authorization.setComponentError(null);
                    enter.setComponentError(null);
                    popup.setPopupVisible(false);
                });

                forButtons.addComponent(enter);
                forButtons.addComponent(authorization);
                forButtons.addComponent(cancel);

                topLayout.addComponent(popup);
                topLayout.setComponentAlignment(popup, Alignment.TOP_RIGHT);

                break;
            case "informationAboutFilm":
                Button back = new Button("Вернуться к списку фильмов");
                back.addClickListener(clickEvent ->  {
                    if (isAuthorization())
                        initFilmsWithAuthorization();
                    else
                        initFilmsWithoutAuthorization();
                });

                topLayout.addComponent(back);
                topLayout.setComponentAlignment(back, Alignment.TOP_LEFT);

                break;
            case "cinemasForFilm":
                Button backToFilms = new Button("Вернуться к списку фильмов");
                backToFilms.addClickListener(clickEvent ->  {
                    if (isAuthorization())
                        initFilmsWithAuthorization();
                    else
                        initFilmsWithoutAuthorization();
                });

                topLayout.addComponent(backToFilms);
                topLayout.setComponentAlignment(backToFilms, Alignment.TOP_LEFT);

                break;
            case "drawRoom":
                Button atBeginingPage = new Button("Вернуться на начальную страницу");
                atBeginingPage.addClickListener(clickEvent ->  {
                    if (isAuthorization())
                        initFilmsWithAuthorization();
                    else
                        initFilmsWithoutAuthorization();
                });

                VerticalLayout buyingTickets = new VerticalLayout();
                HorizontalLayout okAndCancel = new HorizontalLayout();
                PopupView buyPopup = new PopupView("Купить", buyingTickets);

                TextField content = new TextField("email");
                content.setPlaceholder("input email");

                buyPopup.addPopupVisibilityListener(popupVisibilityEvent -> {
                    if (session.getVisitorEmail() != null)
                        content.setValue(session.getVisitorEmail());
                });

                if (isAuthorization())
                    content.setReadOnly(true);

                buyingTickets.addComponent(content);
                buyingTickets.addComponent(okAndCancel);

                Button confirm = new Button("подтвердить");
                confirm.addClickListener(clickEvent ->  {
                    boolean flag = false;

                    List<Place> selectedPLaces = drawingRoom.getSelectedPlace();
                    if (!selectedPLaces.isEmpty()) System.out.println(selectedPLaces.toString());

                    if (selectedPLaces.isEmpty()) {
                        flag = true;
                        confirm.setComponentError(new UserError("Пожалуйста, выберете место("));
                    }
                    if (content.getValue().isEmpty() || !validate(content.getValue())) {
                        flag = true;
                        content.setComponentError(new UserError("Неправильный формат email"));
                    } else {
                        session.setVisitorEmail(content.getValue());
                        session.setVisitorName(new Faker().name().firstName());
                    }
                    if (selectedPLaces.isEmpty() && content.getValue().isEmpty()) {
                        flag = true;
                        confirm.setComponentError(new UserError("Пожалуйста, выберете место и укажите email"));
                    }

                    buyPopup.setPopupVisible(true);

                    if (!flag) {
                        List<Integer> rows = new ArrayList<Integer>();
                        List<Integer> places = new ArrayList<Integer>();
                        List<Integer> prices = new ArrayList<Integer>();
                        for (Place pl : selectedPLaces) {
                            rows.add(pl.getRow());
                            places.add(pl.getPlace());
                            prices.add(pl.getPrice());
                        }
                        session.setPricesOfTickets(prices);
                        session.setTicket_row(rows);
                        session.setTicket_place(places);

                        List<Reserve> reserves = sellTickets(session);
                        if (!reserves.isEmpty()) {
                            redraw();
                            drawingRoom.clearSelectedList();
                            Notification.show("Билеты куплены");
                        }
                        else
                            Notification.show("Что-то пошло не так, билеты не куплены. :)");
                    }
                });

                Button canc = new Button("отменить");
                canc.addClickListener(click -> buyPopup.setPopupVisible(false));

                okAndCancel.addComponent(confirm);
                okAndCancel.addComponent(canc);

                topLayout.addComponent(atBeginingPage);
                topLayout.addComponent(buyPopup);
                topLayout.setComponentAlignment(atBeginingPage, Alignment.TOP_LEFT);
                topLayout.setComponentAlignment(buyPopup, Alignment.TOP_RIGHT);

                break;
        }
        topLayout.setWidth("100%");
        frame.addComponent(topLayout);
    }

    private static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.matches();
    }

    private Film searchFilmByName(String name) {
        List<Film> list = initFilms.getFilms();

        for (Film film : list) {
            String _n = film.getFilm_name();
            boolean flag = name.equals(_n);
            if (flag)
                return film;
        }

        return null;
    }

    public boolean isAuthorization() {
        return authorization;
    }
    public void setAuthorization(boolean authorization) {
        this.authorization = authorization;
    }

    @WebServlet(value = "/*", asyncSupported = true)
    public static class Servlet extends SpringVaadinServlet {
    }

    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    }
}
