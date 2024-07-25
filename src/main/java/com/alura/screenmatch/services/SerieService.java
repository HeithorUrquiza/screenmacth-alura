package com.alura.screenmatch.services;

import com.alura.screenmatch.models.Serie;
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
        return this.dataConversor(this.serieRepository.findTop5ByOrderByEpisodesReleasedDesc());
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
