package com.alura.screenmatch.repositories;

import com.alura.screenmatch.models.Category;
import com.alura.screenmatch.models.Episode;
import com.alura.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
//    Derived queries
    Optional<Serie> findByTitleIgnoreCase(String serieName);
    List<Serie> findTop5ByOrderByRatingDesc();
    List<Serie> findByGenre(Category category);
//    JPQL
    @Query("SELECT ep FROM Serie s JOIN s.episodes ep WHERE ep.title ILIKE %:sentence%")
    List<Episode> episodesBySentence(String sentence);

    @Query("""
            SELECT ep
            FROM Serie s
            JOIN s.episodes ep
            WHERE s.title
            ILIKE %:serieTitle%
            ORDER BY ep.rating
            DESC LIMIT 5
            """)
    List<Episode> getTop5EpisodesBySerie(String serieTitle);

    @Query("""
            SELECT ep
            FROM Serie s
            JOIN s.episodes ep
            WHERE s.title 
            ILIKE %:serieTitle%
            AND YEAR(ep.released) >= :released
            """)
    List<Episode> filterEpisodesBySerieAndYear(String serieTitle, int released);

}