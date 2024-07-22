package com.alura.screenmatch.models;

import java.util.OptionalDouble;

public class Serie {
    private String title;
    private Integer seasons;
    private Double rating;
    private Category genre;
    private String actors;
    private String poster;
    private String plot;

    public Serie(SerieData serieData) {
        this.title = serieData.title();
        this.seasons = serieData.totalSeasons();
        this.rating = OptionalDouble.of(Double.parseDouble(serieData.rating())).orElse(0);
        this.genre = Category.fromString(serieData.genre().split(",")[0].trim());
        this.actors = serieData.actors();
        this.poster = serieData.poster();
        this.plot = serieData.plot();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSeasons() {
        return seasons;
    }

    public void setSeasons(Integer seasons) {
        this.seasons = seasons;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Category getGenre() {
        return genre;
    }

    public void setGenre(Category genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    @Override
    public String toString() {
        return String.format(
                "title=%s, seasons=%s, rating=%.1f, genre=%s, actors=%s, poster=%s, plot=%s",
                this.title, this.seasons, this.rating, this.genre, this.actors, this.poster, this.plot
        );
    }
}
