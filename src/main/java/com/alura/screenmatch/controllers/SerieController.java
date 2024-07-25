package com.alura.screenmatch.controllers;

import com.alura.screenmatch.models.dto.SerieDTO;
import com.alura.screenmatch.services.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/series")
public class SerieController {
    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<SerieDTO> getSeries() {
        return this.serieService.getAllSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> getTop5() {
        return this.serieService.getTop5Series();
    }

    @GetMapping("/releases")
    public List<SerieDTO> getReleases() {
        return this.serieService.getReleases();
    }

    @GetMapping("/{id}")
    public SerieDTO getById(@PathVariable Long id) {
        return this.serieService.getSerieById(id);
    }
}
