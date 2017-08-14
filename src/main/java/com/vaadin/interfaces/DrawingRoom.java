package com.vaadin.interfaces;

import com.vaadin.domain.Film;
import com.vaadin.domain.Room;
import com.vaadin.domain.Show;
import com.vaadin.domain.Ticket;
import com.vaadin.domain.drawing.Place;
import com.vaadin.service.utils.RequestService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.junit.Test;
import org.vaadin.hezamu.canvas.Canvas;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Nik on 08.07.2017.
 */
@SpringComponent
@UIScope
public class DrawingRoom {
    private Show show;
    private Film film;
    private Room room;

    List<Place> placeList = new ArrayList<>();
    List<Place> selectedPlace = new ArrayList<>();

    private int width = 0;
    private int height = 0;

    Integer betw = 3;
    private Point size;
    private Point start = new Point(100, 100);
    private Point sizeHelp = new Point(20, 20);
    private Point work;

    private final static String noVipPlaceColor = "green";
    private final static String vipPlaceColor = "orange";
    private final static String boughtPlaceColor = "grey";
    private final static String selectedPlaceColor = "blue";
    private final static String defaultColor = "black";
    private final static String clearColor = "white";

    public DrawingRoom() {
        placeList.clear();
        clearSelectedList();
    }

//    public DrawingRoom(int height, int width) {
//        this.height = height;
//        this.width = width;
//
//        work = new Point(width - 400, height - 300);
//
//        if (size != null && room != null)
//            size = new Point((int) work.getX() / room.getMax_place() - betw,
//                    (int) work.getY() / room.getMax_row() - betw);
//    }

    public void draw(Canvas canvas) {
        clearSelectedList();

        canvas.saveContext();
        canvas.clear();
        canvas.moveTo(0, 0);

        descriptionPlaces(canvas);
        drawRoom(canvas);

        canvas.restoreContext();
    }

    /**
     * отрисовка описания мест зала, отображенных пользователю
     * @param canvas
     */
    private void descriptionPlaces(Canvas canvas) {
        canvas.setGlobalAlpha(0.4);
        canvas.setFillStyle(noVipPlaceColor);
        canvas.fillRect(1.5 * start.getX() + work.getX(), 20, sizeHelp.getX(), sizeHelp.getY());
        canvas.setGlobalAlpha(1);
        canvas.setFillStyle(defaultColor);
        canvas.fillText(" - не вип места (" + (int) (film.getFilm_price().intValue() * room.getRoom_not_vip_coef()) + " рублей)",
                1.5 * start.getX() + work.getX() + sizeHelp.getX(), 30 + betw, 150);

        canvas.setGlobalAlpha(0.4);
        canvas.setFillStyle(vipPlaceColor);
        canvas.fillRect(1.5 * start.getX() + work.getX(),
                20 + sizeHelp.getY() + betw, sizeHelp.getX(), sizeHelp.getY());
        canvas.setGlobalAlpha(1);
        canvas.setFillStyle(defaultColor);
        canvas.fillText(" - вип места (" + (int) (film.getFilm_price() * room.getRoom_vip_coef()) + " рублей)",
                1.5 * start.getX() + work.getX() + sizeHelp.getX(), 30 + 2 * betw + sizeHelp.getY(), 150);

        canvas.setGlobalAlpha(0.4);
        canvas.setFillStyle(boughtPlaceColor);
        canvas.fillRect(1.5 * start.getX() + work.getX(),
                20 + 2 * (sizeHelp.getY() + betw), sizeHelp.getX(), sizeHelp.getY());
        canvas.setGlobalAlpha(1);
        canvas.setFillStyle(defaultColor);
        canvas.fillText(" - купленные места",
                1.5 * start.getX() + work.getX() + sizeHelp.getX(), 30 + betw + 2 * (betw + sizeHelp.getY()), 90);

        canvas.setGlobalAlpha(0.4);
        canvas.setFillStyle(selectedPlaceColor);
        canvas.fillRect(1.5 * start.getX() + work.getX(),
                20 + 3 * (sizeHelp.getY() + betw), sizeHelp.getX(), sizeHelp.getY());
        canvas.setGlobalAlpha(1);
        canvas.setFillStyle(defaultColor);
        canvas.fillText(" - выбранные места",
                1.5 * start.getX() + work.getX() + sizeHelp.getX(), 30 + betw + 3 * (betw + sizeHelp.getY()), 90);
    }

    /**
     * отрисовка мест зала
     * @param canvas
     */
    private void drawRoom(Canvas canvas) {
        getPlaceList();
        fillCoordsPlace();

        fillColorPlaces(canvas);
    }

    /**
     * получение списка мест зала
     * возможно получение без координат мест
     * @return
     */
    public List<Place> getPlaceList() {
        placeList.clear();
        placeList.addAll(plList());
        return placeList;
    }

    /**
     * заполнение списка мест заданного зала
     * @return
     */
    private List<Place> plList() {
        List<Place> pl = new ArrayList<>();
        List<Ticket> boughtTickets = RequestService.getBoughtTickets(show.getShow_id());

        int n = 1;
        int beginVipRow =  -1, endVipRow = -1;
        AverageRowPlace rowPlace = AverageRowPlace.getAverageRow(room.getMax_row());
        AverageSitPlace sitPlace = AverageSitPlace.getAverageSit(room.getMax_place());
        int beginVipSit = sitPlace.startingSit, endVipSit = sitPlace.startingSit + sitPlace.sizeVipSit;

        if (rowPlace != null) {
            beginVipRow = rowPlace.startingRow;
            endVipRow = rowPlace.startingRow + rowPlace.sizeVipRow;
        }

        for (int i = 1; i <= room.getMax_row(); i++) {
            for (int j = 1; j <= room.getMax_place(); j++) {
                Place place = new Place();

                place.setId(n); n++;
                place.setRow(i); place.setPlace(j);
                place.setPrice((int) (film.getFilm_price().intValue() * room.getRoom_not_vip_coef()));

                if (beginVipRow != -1 && i == beginVipRow && beginVipRow <= endVipRow)
                    if (j >= beginVipSit && beginVipSit <= endVipSit) {
                        place.setPrice((int) (film.getFilm_price() * room.getRoom_vip_coef()));
                        place.setVip(true);
                        beginVipSit++;
                    }

                if (isBoughtTicket(boughtTickets, i, j))
                    place.setBought(true);

                pl.add(place);
            }

            if (beginVipRow != -1) {
                beginVipSit = sitPlace.startingSit;
                if (i == beginVipRow && beginVipRow <= endVipRow)
                    beginVipRow++;
                else if (i > beginVipRow)
                    beginVipRow = rowPlace.startingRow;
            }
        }

        return pl;
    }

    private boolean isBoughtTicket(List<Ticket> boughtTickets, int rowId, int placeId) {
        for (Ticket t : boughtTickets)
            if (t.getTicket_row() == rowId && t.getTicket_place() == placeId)
                return true;

        return false;
    }

    /**
     * заполнение мест в зале по координатам - местоположение на канвасе
     */
    private void fillCoordsPlace() {
        int betw = 3;
        int rows = room.getMax_row(), places = room.getMax_place();

        Point start = new Point(100, 20);
        Point work = new Point(width - 400, height - 300);
        Point size = new Point((int) work.getX() / places - betw, (int) work.getY() / rows - betw);

        for (Place  pl : placeList) {
            Point p1 = new Point((int) (start.getX() + (pl.getPlace() - 1) * (size.getX() + betw)),
                    (int) (start.getY() + (rows - pl.getRow()) * (size.getY() + 4 * betw)));
            Point p2 = new Point((int) (p1.getX() + size.getX()), (int) (p1.getY() + size.getY() + 5));

            pl.setllru(p1, p2);
        }
    }

    private void fillColorPlaces(Canvas canvas) {
        int sum = 0;
        for (Place  pl : placeList) {
            canvas.setGlobalAlpha(0.5);
            canvas.setFillStyle(chooseColor(pl));
            canvas.fillRect(pl.getLl().getX(), pl.getLl().getY(),
                    pl.getRu().getX() - pl.getLl().getX(), pl.getRu().getY() - pl.getLl().getY() + betw);

            canvas.setGlobalAlpha(1);
            canvas.setFillStyle(defaultColor);
            canvas.fillText("Ряд № " + pl.getRow(), 20, pl.getLl().getY() + 4 * betw, 120);
            canvas.fillText(pl.getPlace() + "", pl.getLl().getX() + betw, pl.getLl().getY() + 4 * betw, 10);
        }

        canvas.setGlobalAlpha(1);
        canvas.setFillStyle(defaultColor);
        canvas.fillText("Сумма: " + sum + " рублей",
                1.5 * start.getX() + work.getX(), 20 + 4 * (sizeHelp.getY() + 2 * betw), 150);
        canvas.fillText("Экран", 70, height - 150, 70);
        canvas.fillRect(70, height - 135, width - 350, 16);
    }

    private String chooseColor(Place place) {
        String color = noVipPlaceColor;

        if (place.isVip())
            color = vipPlaceColor;
        if (place.isBought())
            color = boughtPlaceColor;
        if (place.isSelected())
            color = selectedPlaceColor;

        return color;
    }

    /**
     * при нажатии на какое либо место определяется не выбрано ли оно ранее
     * закрашивается соответствующим цветом
     * @param canvas
     * @param point
     */
    public void fillSelectedPlace(Canvas canvas, Point point) {
        Place select = checkSelectionsReturnEnterPoint(point);
        if (select != null) {
            /* clear current place */
            canvas.setGlobalAlpha(1);
            canvas.setFillStyle(clearColor);
            canvas.fillRect(select.getLl().getX(), select.getLl().getY(),
                    select.getRu().getX() - select.getLl().getX(),
                    select.getRu().getY() - select.getLl().getY() + betw);
            /* clean price */
            canvas.fillRect(1.5 * start.getX() + work.getX(), 2 * betw + 4 * (sizeHelp.getY() + 2 * betw), 150, 20);

            /* draw new color consider state of the place */
            canvas.setGlobalAlpha(0.5);
            canvas.setFillStyle(chooseColor(select));
            canvas.fillRect(select.getLl().getX(), select.getLl().getY(),
                    select.getRu().getX() - select.getLl().getX(),
                    select.getRu().getY() - select.getLl().getY() + betw);

            canvas.setGlobalAlpha(1);
            canvas.setFillStyle(defaultColor);
            canvas.fillText(select.getPlace() + "", select.getLl().getX() + betw,
                    select.getLl().getY() + 4 * betw, 10);

            if (select.isSelected())
                selectedPlace.add(select);
            else
                selectedPlace.remove(select);

            canvas.fillText("Сумма: " + getSumSelectedPlaces(selectedPlace) + " рублей",
                    1.5 * start.getX() + work.getX(), 20 + 4 * (sizeHelp.getY() + 2 * betw), 150);
        }
    }

    private int getSumSelectedPlaces(List<Place> places) {
        int sum = 0;

        for (Place pl : places)
            sum += pl.getPrice();

        return sum;
    }

    private Place checkSelectionsReturnEnterPoint(Point p1) {
        for (Place place : placeList)
            if (isInside(place.getLl(), place.getRu(), p1) && !place.isBought()) {
                if (!place.isSelected())
                    place.setSelected(true);
                else
                    place.setSelected(false);
                return place;
            }
        return null;
    }

    private boolean isInside(Point p1, Point p2, Point p) {
        int step = (int) (p2.getY() - p1.getY());
        Point newP1 = null;
        Point newP2 = null;
        if (room.getMax_row() == 12 && room.getMax_place() == 15)
        {
            newP1 = new Point((int) p1.getX(),
                    (int) (p1.getY() + start.getY() + sizeHelp.getY() - step - 10 - betw));
            newP2 = new Point((int) p2.getX(),
                    (int) (p2.getY() + start.getY() + sizeHelp.getY() - step - 5));
        }
        if (room.getMax_row() == 12 && room.getMax_place() == 8) {
            newP1 = new Point((int) p1.getX(),(int) (p1.getY() + start.getY() - sizeHelp.getY() + 5));
            newP2 = new Point((int) p2.getX(),(int) (p2.getY() + 3 * step + sizeHelp.getY() + betw));
        }
        if (room.getMax_row() == 4 && room.getMax_place() == 4) {
            newP1 = new Point((int) p1.getX(),(int) (p1.getY() + step + sizeHelp.getY() + 2 * betw));
            newP2 = new Point((int) p2.getX(),(int) (p2.getY() + step + sizeHelp.getY() + betw));
        }
        if (room.getMax_row() == 11 && room.getMax_place() == 11) {
            newP1 = new Point((int) p1.getX(),
                    (int) (p1.getY() + start.getY() + sizeHelp.getY() - step - 10 - betw));
            newP2 = new Point((int) p2.getX(),
                    (int) (p2.getY() + start.getY() + sizeHelp.getY() - step - betw));
        }
        if (room.getMax_row() == 2 && room.getMax_place() == 6) {
            newP1 = new Point((int) p1.getX(), (int) (p1.getY() + step - 2 * sizeHelp.getY()));
            newP2 = new Point((int) p2.getX(), (int) (p2.getY() + step - 2 * sizeHelp.getY() + 2 * betw));
        }

        if (p.getX() >= newP1.getX() && p.getX() <= newP2.getX() || p.getX() <= newP1.getX() && p.getX() >= newP2.getX())
                if (p.getY() >= newP1.getY() && p.getY() <= newP2.getY() || p.getY() <= newP1.getY() && p.getY() >= newP2.getY())
                    return true;
            return false;
    }

    public Room getRoom() {
        return room;
    }
    public Film getFilm() {
        return film;
    }
    public Show getShow() {
        return show;
    }
    public List<Place> getSelectedPlace() {
        return selectedPlace;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
    public void setShow(Show show) {
        this.show = show;
        room = RequestService.getMaxPositionOfRoom(show.getRoom_id());
        if (work != null)
            size = new Point((int) work.getX() / room.getMax_place() - betw,
                    (int) work.getY() / room.getMax_row() - betw);
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        work = new Point(width - 400, height - 300);
        if (size != null && room != null)
            size = new Point((int) work.getX() / room.getMax_place() - betw,
                    (int) work.getY() / room.getMax_row() - betw);
    }

    /**
     * классы для поиска вип мест в зале - ряд, место
     */
    static class AverageRowPlace {
        public int startingRow = 0;
        public int sizeVipRow = 0;

        public static AverageRowPlace getAverageRow(int countRow) {
            int div = 2;
            double eps = 0.01;
            AverageRowPlace rowPlace = new AverageRowPlace();

            if (countRow <= 3) {
                return null;
            }else if (countRow > 3 && countRow <= 5) {
                rowPlace.startingRow = (int)Math.floor(countRow/div - eps) + 1;
                rowPlace.sizeVipRow = div - 1;
            } else if (countRow < 9) {
                rowPlace.startingRow = (int)Math.floor(countRow/div + eps) - 1;
                rowPlace.sizeVipRow = div;
            } else {
                rowPlace.startingRow = (int) Math.floor(countRow / div + eps) - 2;
                rowPlace.sizeVipRow = div * 2;
            }

            return rowPlace;
        }
    }
    static class AverageSitPlace {
        public int startingSit = 0;
        public int sizeVipSit = 0;

        public static AverageSitPlace getAverageSit(int countPlaces) {
            int div = 2;
            double eps = 0.01;
            AverageSitPlace sitPlace = new AverageSitPlace();

            if (countPlaces > 3 && countPlaces < 8) {
                sitPlace.startingSit = (int)Math.floor(countPlaces / div - eps) + 1;
                sitPlace.sizeVipSit = div - 1;
            } else if (countPlaces <= 10) {
                sitPlace.startingSit = (int)Math.floor(countPlaces / div - eps);
                sitPlace.sizeVipSit = div + 1;
            } else if (countPlaces < 12) {
                sitPlace.startingSit = (int) Math.floor(countPlaces / div + eps) - 1;
                sitPlace.sizeVipSit = div * 2;
            } else if (countPlaces < 16) {
                sitPlace.startingSit = (int) Math.floor(countPlaces / div + eps) - 3 + 1;
                sitPlace.sizeVipSit = div * 3;
            }

            return sitPlace;
        }
    }

    public void clearSelectedList() {
        selectedPlace.clear();
    }

    private void checkSelections(Point p1) {
        for (Place place : placeList)
            if (isInside(place.getLl(), place.getRu(), p1) && !place.isBought()) {
                if (!place.isSelected())
                    place.setSelected(true);
                else
                    place.setSelected(false);
            }
    }
    private List<Place> checkSelectionsAndReturnEnterPoints(List<Point> points) {
        List<Place> result = new ArrayList<>();

        for (Point p : points)
            for (Place place : placeList)
                if (isInside(place.getLl(), place.getRu(), p) && !place.isBought()) {
                    if (!place.isSelected()) {
                        place.setSelected(true);
                        result.add(place);
                    }
                    else {
                        place.setSelected(false);
                        result.remove(place);
                    }
                }

        return result;
    }

    @Test
    public void testBoughtTicket() {
        List<Ticket> testListTIckets = new ArrayList<Ticket>() {
            {
                add(new Ticket(0, null, 5, 5));
                add(new Ticket(1, null, 5, 3));
                add(new Ticket(2, null, 3, 5));
                add(new Ticket(3, null, 2, 7));
            }
        };

        assertTrue(isBoughtTicket(testListTIckets, 5, 5));
        assertTrue(isBoughtTicket(testListTIckets, 5, 3));
        assertTrue(isBoughtTicket(testListTIckets, 3, 5));
        assertTrue(isBoughtTicket(testListTIckets, 2, 7));
    }

    List<Place> _pl = new ArrayList<Place>() {
        {
            add(new Place());
            get(0).setVip(true); get(0).setPrice(100);
            add(new Place());
            get(1).setBought(true); get(1).setPrice(150);
            add(new Place());
            get(2).setSelected(true); get(2).setPrice(200);
        }
    };
    @Test
    public void testColor() {
        String c1 = vipPlaceColor;
        String c2 = boughtPlaceColor;
        String c3 = selectedPlaceColor;

        for (Place p : _pl)
            assertNotNull(chooseColor(p));

        assertEquals(c1, chooseColor(_pl.get(0)));
        assertEquals(c2, chooseColor(_pl.get(1)));
        assertEquals(c3, chooseColor(_pl.get(2)));
    }

    @Test
    public void testSumSelected() {
        assertTrue(getSumSelectedPlaces(_pl) > 0);
    }
}

