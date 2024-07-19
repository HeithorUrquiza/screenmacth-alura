package com.alura.screenmatch.principal;

import com.alura.screenmatch.models.Episode;
import com.alura.screenmatch.models.EpisodeData;
import com.alura.screenmatch.models.SeasonData;
import com.alura.screenmatch.models.SerieData;
import com.alura.screenmatch.services.ApiConsumer;
import com.alura.screenmatch.services.DataConversor;
import io.github.cdimascio.dotenv.Dotenv;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ApiConsumer apiConsumer = new ApiConsumer();
    private DataConversor dataConversor = new DataConversor();
    private List<SeasonData> seasons = new ArrayList<>();
    private List<Episode> episodes = new ArrayList<>();

    // Constants
    private final Dotenv dotenv = Dotenv.load();
    private final String API_KEY = dotenv.get("IMDB_API_KEY");

    public void starter() {
        this.collectData();
        this.episodes = this.populate();
//        this.showAllEpisodesTitles(episodes);
//        this.getTop5Episodes(episodes);
        this.getEpisodesFromYear(episodes);
    }

    public void collectData() {
        final String BASE_URL = "https://www.omdbapi.com/?t=";
        System.out.print("Insert the series name: ");
        String serieName = this.scanner.nextLine();

        // Getting serie data
        String serieJson = this.apiConsumer.getData(
                BASE_URL +
                        serieName.replace(" ", "+") +
                        "&apiKey=" + API_KEY
        );
        SerieData serieData = this.dataConversor.getData(serieJson, SerieData.class);

        // Getting season data
        for (int i = 1; i <= serieData.totalSeasons(); i++) {
            String seasonJson = this.apiConsumer.getData(
                    BASE_URL +
                            serieName.replace(" ", "+") +
                            "&season=" + i +
                            "&apiKey=" + API_KEY
            );
            this.seasons.add(dataConversor.getData(seasonJson, SeasonData.class));
        }
    }

    public List<Episode> populate() {
        return this.seasons.stream()
                .flatMap(season -> season.episodes().stream()
                        .map(episode -> new Episode(season.season(), episode)))
                .collect(Collectors.toList());
    }

    public void showAllEpisodesTitles(List<Episode> episodes) {
        episodes.stream()
                .sorted(Comparator.comparing(Episode::getSeason))
                .forEach(episode -> System.out.printf("S%d - %s\n", episode.getSeason(), episode.getTitle()));
    }

    public void getTop5Episodes(List<Episode> episodes) {
        System.out.println("Top 5 episodes !!");
        episodes.stream()
                .sorted(Comparator.comparing(Episode::getRating).reversed())
                .limit(5)
                .forEach(System.out::println);
    }

    public void getEpisodesFromYear(List<Episode> episodes) {
        System.out.print("Chose a year for search: ");
        int year = this.scanner.nextInt();
        LocalDate searchDate = LocalDate.of(year, 1, 1);
        episodes.stream()
                .filter(episode -> episode.getReleased() != null && episode.getReleased().isAfter(searchDate))
                .forEach(episode -> System.out.printf("Season: %d | Episode: %d | Released: %s\n",
                        episode.getSeason(), episode.getEpisodeNumber(), episode.getReleased()));
    }
}
