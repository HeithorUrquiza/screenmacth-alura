package com.alura.screenmatch.services;

import com.alura.screenmatch.models.Category;
import com.alura.screenmatch.models.Episode;
import com.alura.screenmatch.models.Serie;
import com.alura.screenmatch.models.dto.EpisodeDTO;
import com.alura.screenmatch.models.dto.SerieDTO;
import com.alura.screenmatch.repositories.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> getAllSeries() {
        return this.dataConversor(this.serieRepository.findAll());
    }

    public List<SerieDTO> getTop5Series() {
        return this.dataConversor(this.serieRepository.findTop5ByOrderByRatingDesc());
    }

    public List<SerieDTO> getReleases() {
        return this.dataConversor(this.serieRepository.currentReleases());
    }

    public SerieDTO getSerieById(Long id) {
        Optional<Serie> optionalSerie = this.serieRepository.findById(id);
        if (optionalSerie.isPresent()) {
            Serie serie = optionalSerie.get();
            return new SerieDTO(
                    serie.getId(),
                    serie.getTitle(),
                    serie.getSeasons(),
                    serie.getRating(),
                    serie.getGenre(),
                    serie.getActors(),
                    serie.getPoster(),
                    serie.getPlot()
            );
        }
        return null;
    }

//    Return the episodes for all seasons
    public List<EpisodeDTO> getAllSeasons(Long id) {
        Optional<Serie> optionalSerie = this.serieRepository.findById(id);
        if (optionalSerie.isPresent()) {
            Serie serie = optionalSerie.get();
            return serie.getEpisodes().stream()
                    .map(ep -> new EpisodeDTO(ep.getSeason(), ep.getEpisodeNumber(), ep.getTitle()))
                    .collect(Collectors.toList());
        }
        return null;
    }

//  Return a list of episodes that belongs a specific season
    public List<EpisodeDTO> getEpisodesBySeason(Long id, Long seasonNumber) {
        return this.serieRepository.getEpisodeBySeason(id, seasonNumber).stream()
                .map(ep -> new EpisodeDTO(ep.getSeason(), ep.getEpisodeNumber(), ep.getTitle()))
                .collect(Collectors.toList());
    }

//    Return a list of series based in a genre
    public List<SerieDTO> getSerieByGenre(String genre) {
        Category category = Category.getCategory(genre);
        return this.dataConversor(this.serieRepository.findByGenre(category));
    }

//    Get the top 5 episodes based one serie
    public List<EpisodeDTO> getTop5EpisodesBySerie(Long id) {
        return this.serieRepository.getTop5EpisodesBySerie(id).stream()
                .map(ep -> new EpisodeDTO(ep.getSeason(), ep.getEpisodeNumber(), ep.getTitle()))
                .collect(Collectors.toList());
    }

//    Function that convert to SerieDTO type
    private List<SerieDTO> dataConversor(List<Serie> series) {
        return series.stream()
                .map(serie -> new SerieDTO(
                        serie.getId(),
                        serie.getTitle(),
                        serie.getSeasons(),
                        serie.getRating(),
                        serie.getGenre(),
                        serie.getActors(),
                        serie.getPoster(),
                        serie.getPlot()
                ))
                .collect(Collectors.toList());
    }
}
