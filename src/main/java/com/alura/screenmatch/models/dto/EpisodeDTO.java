package com.alura.screenmatch.models.dto;

public record EpisodeDTO(
        Integer season,
        Integer episode,
        String title
) {}