package com.alura.screenmatch.principal;

import com.alura.screenmatch.models.SeasonData;
import com.alura.screenmatch.models.Serie;
import com.alura.screenmatch.models.SerieData;
import com.alura.screenmatch.services.ApiConsumer;
import com.alura.screenmatch.services.DataConversor;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ApiConsumer apiConsumer = new ApiConsumer();
    private DataConversor dataConversor = new DataConversor();
    private List<SeasonData> seasons = new ArrayList<>();
    private List<Serie> series = new ArrayList<>();

//    Constants
    private final Dotenv dotenv = Dotenv.load();
    private final String BASE_URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = String.format("&apiKey=%s", dotenv.get("OMDB_API_KEY"));

    public void showMenu() throws InterruptedException, IOException {
        String menu = """
                ---
                1 - Search series
                2 - Search episodes
                3 - List all series searched
                0 - Leave
                """;

        boolean stop = false;
        while (!stop) {
            System.out.printf("%s\n-> ", menu);
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    searchSeries();
                    break;
                case 2:
                    searchEpisodes();
                    break;
                case 3:
                    listAllSeriesSearched();
                    break;
                case 0:
                    System.out.println("Leaving...");
                    stop = true;
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void searchSeries() {
        SerieData serieData = getSerieData();
        this.series.add(new Serie(serieData));
        System.out.println(serieData);
        System.out.println();
    }

    private SerieData getSerieData() {
        System.out.print("Type the series name: ");
        String serieName = scanner.nextLine();
        String serieJson = this.apiConsumer.getData(
                BASE_URL +
                        serieName.replace(" ", "+") +
                        API_KEY
        );
        return this.dataConversor.getData(serieJson, SerieData.class);
    }

    private void searchEpisodes() {
        SerieData serieData = getSerieData();
        for (int i = 1; i <= serieData.totalSeasons(); i++) {
            String seasonJson = this.apiConsumer.getData(
                    BASE_URL +
                            serieData.title().replace(" ", "+") +
                            "&season=" + i + API_KEY
            );
            this.seasons.add(this.dataConversor.getData(seasonJson, SeasonData.class));
        }
        this.seasons.forEach(System.out::println);
        System.out.println();
    }

    private void listAllSeriesSearched() {
        this.series.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
    }
}
