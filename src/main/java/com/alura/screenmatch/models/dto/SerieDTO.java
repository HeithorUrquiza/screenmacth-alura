package com.alura.screenmatch.models.dto;

import com.alura.screenmatch.models.Category;

// This class is responsable for the JSON return
public record SerieDTO(
        Long id,
        String title,
        Integer seasons,
        Double rating,
        Category category,
        String actors,
        String poster,
        String plot
) {}
