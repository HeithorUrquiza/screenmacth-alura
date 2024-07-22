package com.alura.screenmatch.models;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.OptionalDouble;

public class Episode {
    private Integer season;
    private String title;
    private Integer episodeNumber;
    private Double rating;
    private LocalDate released;

    public Episode(Integer season, EpisodeData episodeData) {
        this.season = season;
        this.title = episodeData.title();
        this.episodeNumber = episodeData.episode();
        this.rating = OptionalDouble.of(Double.parseDouble(episodeData.rating())).orElse(0);
        try {
            // DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.released = LocalDate.parse(episodeData.released());
        }
        catch(DateTimeParseException exception) {
            this.released = null;
        }
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getReleased() {
        return released;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    @Override
    public String toString() {
        return String.format("season=%d, title=%s, episodeNumber=%d, rating=%.1f, released=%s",
                this.season, this.title, this.episodeNumber, this.rating, this.released);
    }
}

