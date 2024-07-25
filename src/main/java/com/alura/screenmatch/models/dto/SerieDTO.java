package com.alura.screenmatch.models.dto;

import com.alura.screenmatch.models.Category;

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
