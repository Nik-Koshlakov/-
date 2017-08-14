package kinoService.domain;

import java.util.Date;

/**
 * Created by Nik on 19.06.2017.
 */
public class Movie {
    private String nameMovie;
    private String posterMoviePath;
    private Date beginning_time;
    private String countryFilm;
    private String yearFilm;
    private String trailerFilm;
    private String ageFilm;
    private String directorFilm;

    private String nameCinema;
    private String addressCinema;
    private String phone;
    private String stationAboutCinema;
    private String posterCinemaPath;

    private Boolean imax;

    private Double price;
    private String description;
    private Integer running_time;

    public Movie() {
    }

    public Movie(Movie movie) {
        nameMovie = movie.getNameMovie();
        posterMoviePath = movie.getPosterMoviePath();
        beginning_time = movie.getBeginning_time();
        countryFilm = movie.getCountryFilm();
        yearFilm = movie.getYearFilm();
        trailerFilm = movie.getTrailerFilm();
        ageFilm = movie.getAgeFilm();
        directorFilm = movie.getDirectorFilm();

        nameCinema = movie.getNameCinema();
        addressCinema = movie.getAddressCinema();
        phone = movie.getPhone();
        stationAboutCinema = movie.getStationAboutCinema();
        posterCinemaPath = movie.getPosterCinemaPath();

        imax = movie.getImax();

        price = movie.getPrice();
        description = movie.getDescription();
        running_time = movie.getRunning_time();
    }

    public Movie(String nameMovie, String posterMoviePath, Date beginning_time, String posterCinemaPath,
                 String countryFilm, String yearFilm, String trailerFilm, String ageFilm,
                 String directorFilm, String nameCinema, String addressCinema, String phone,
                 String stationAboutCinema, Boolean imax, Double price, String description, Integer running_time) {
        this.nameMovie = nameMovie;
        this.posterMoviePath = posterMoviePath;
        this.beginning_time = beginning_time;
        this.countryFilm = countryFilm;
        this.yearFilm = yearFilm;
        this.trailerFilm = trailerFilm;
        this.ageFilm = ageFilm;
        this.directorFilm = directorFilm;
        this.nameCinema = nameCinema;
        this.addressCinema = addressCinema;
        this.posterCinemaPath = posterCinemaPath;
        this.phone = phone;
        this.stationAboutCinema = stationAboutCinema;
        this.imax = imax;
        this.price = price;
        this.description = description;
        this.running_time = running_time;
    }

    public String getPosterCinemaPath() {
        return posterCinemaPath;
    }

    public void setPosterCinemaPath(String posterCinemaPath) {
        this.posterCinemaPath = posterCinemaPath;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public String getPosterMoviePath() {
        return posterMoviePath;
    }

    public void setPosterMoviePath(String posterMoviePath) {
        this.posterMoviePath = posterMoviePath;
    }

    public String getNameCinema() {
        return nameCinema;
    }

    public void setNameCinema(String nameCinema) {
        this.nameCinema = nameCinema;
    }

    public String getAddressCinema() {
        return addressCinema;
    }

    public void setAddressCinema(String addressCinema) {
        this.addressCinema = addressCinema;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStationAboutCinema() {
        return stationAboutCinema;
    }

    public void setStationAboutCinema(String stationAboutCinema) {
        this.stationAboutCinema = stationAboutCinema;
    }

    public Boolean getImax() {
        return imax;
    }

    public void setImax(Boolean imax) {
        this.imax = imax;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRunning_time() {
        return running_time;
    }

    public void setRunning_time(Integer running_time) {
        this.running_time = running_time;
    }

    public Date getBeginning_time() {
        return beginning_time;
    }

    public void setBeginning_time(Date beginning_time) {
        this.beginning_time = beginning_time;
    }

    public String getCountryFilm() {
        return countryFilm;
    }

    public void setCountryFilm(String countryFilm) {
        this.countryFilm = countryFilm;
    }

    public String getYearFilm() {
        return yearFilm;
    }

    public void setYearFilm(String yearFilm) {
        this.yearFilm = yearFilm;
    }

    public String getTrailerFilm() {
        return trailerFilm;
    }

    public void setTrailerFilm(String trailerFilm) {
        this.trailerFilm = trailerFilm;
    }

    public String getAgeFilm() {
        return ageFilm;
    }

    public void setAgeFilm(String ageFilm) {
        this.ageFilm = ageFilm;
    }

    public String getDirectorFilm() {
        return directorFilm;
    }

    public void setDirectorFilm(String directorFilm) {
        this.directorFilm = directorFilm;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "nameMovie='" + nameMovie + '\'' +
                ", beginning_time=" + beginning_time +
                ", countryFilm='" + countryFilm + '\'' +
                ", yearFilm='" + yearFilm + '\'' +
                ", ageFilm='" + ageFilm + '\'' +
                ", directorFilm='" + directorFilm + '\'' +
                ", nameCinema='" + nameCinema + '\'' +
                ", addressCinema='" + addressCinema + '\'' +
                ", phone='" + phone + '\'' +
                ", stationAboutCinema='" + stationAboutCinema + '\'' +
                ", price=" + price +
                ", running_time=" + running_time +
                '}';
    }
}
