package com.alura.screenmatch.models;

import com.alura.screenmatch.repositories.SerieRepository;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodes")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer season;
    private String title;
    private Integer episodeNumber;
    private Double rating;
    private LocalDate released;
    @ManyToOne
    @JoinColumn(name = "serie_id", nullable = false)
    private Serie serie;

    public Episode() {}

    public Episode(Integer season, EpisodeData episodeData) {
        this.season = season;
        this.title = episodeData.title();
        this.episodeNumber = episodeData.episode();
        try {
            this.rating = Double.valueOf(episodeData.rating());
        }
        catch (NumberFormatException ex) {
            this.rating = 0.0;
        }
        try {
            // DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.released = LocalDate.parse(episodeData.released());
        }
        catch(DateTimeParseException exception) {
            this.released = null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
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

