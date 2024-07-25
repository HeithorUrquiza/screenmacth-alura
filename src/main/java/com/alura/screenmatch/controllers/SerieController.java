package com.alura.screenmatch.controllers;

import com.alura.screenmatch.models.dto.SerieDTO;
import com.alura.screenmatch.repositories.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SerieController {
    @Autowired
    private SerieRepository serieRepository;

    @GetMapping("/series")
    public List<SerieDTO> getSeries() {
        return serieRepository.findAll()
                .stream()
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
