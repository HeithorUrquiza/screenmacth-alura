package com.alura.screenmatch.principal;

import com.alura.screenmatch.models.*;
import com.alura.screenmatch.repositories.SerieRepository;
import com.alura.screenmatch.services.ApiConsumer;
import com.alura.screenmatch.services.DataConversor;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ApiConsumer apiConsumer = new ApiConsumer();
    private DataConversor dataConversor = new DataConversor();
    private List<SeasonData> seasons = new ArrayList<>();
    private List<Serie> series = new ArrayList<>();
    private SerieRepository serieRepository;

//    Constants
    private final Dotenv dotenv = Dotenv.load();
    private final String BASE_URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = String.format("&apiKey=%s", dotenv.get("OMDB_API_KEY"));

    public Principal(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public void showMenu() throws InterruptedException, IOException {
        String menu = """
                ---
                1 - Search series
                2 - Search episodes
                3 - List all series searched
                4 - Search serie by title
                5 - Show top 5 series
                6 - Search serie by genre
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
                case 4:
                    searchSerieByTitle();
                    break;
                case 5:
                    getTop5Series();
                    break;
                case 6:
                    searchSerieByGenre();
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
        this.serieRepository.save(new Serie(serieData));
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
        this.listAllSeriesSearched();
        System.out.print("Choose a serie by its name: ");
        String serieName = scanner.nextLine();
        Optional<Serie> optionalSerie = this.serieRepository.findByTitleIgnoreCase(serieName);

        if (optionalSerie.isPresent()) {
            Serie foundedSerie = optionalSerie.get();
            for (int i = 1; i <= foundedSerie.getSeasons(); i++) {
                String seasonJson = this.apiConsumer.getData(
                        BASE_URL +
                                foundedSerie.getTitle().replace(" ", "+") +
                                "&season=" + i + API_KEY
                );
                this.seasons.add(this.dataConversor.getData(seasonJson, SeasonData.class));
            }
            this.seasons.forEach(System.out::println);
            System.out.println();

            List<Episode> episodes = this.seasons.stream()
                    .flatMap(seasonData -> seasonData.episodes().stream()
                            .map(episodeData -> new Episode(seasonData.season(), episodeData)))
                    .collect(Collectors.toList());

            foundedSerie.setEpisodes(episodes);
            serieRepository.save(foundedSerie);
        }
        else {
            System.out.println("Serie not founded");
        }

    }

    private void listAllSeriesSearched() {
        this.series = this.serieRepository.findAll();
        this.series.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
    }

    private void searchSerieByTitle() {
        System.out.print("Type a serie name: ");
        String serieName = this.scanner.nextLine();
        Optional<Serie> opitionalSerie = this.serieRepository.findByTitleIgnoreCase(serieName);
        if (opitionalSerie.isPresent()) {
            System.out.printf("Serie data: %s", opitionalSerie.get());
        }
        else {
            System.out.println("Serie not founded");
        }
        System.out.println();
    }

    private void getTop5Series() {
        List<Serie> series = this.serieRepository.findTop5ByOrderByRatingDesc();
        series.forEach(serie -> System.out.printf("Title: %s | Rating: %.1f\n", serie.getTitle(), serie.getRating()));
        System.out.println();
    }

    private void searchSerieByGenre() {
        System.out.print("Type category name: ");
        String categoryName = this.scanner.nextLine();
        Category category = Category.getCategory(categoryName);
        List<Serie> series = this.serieRepository.findByGenre(category);
        series.forEach(serie -> System.out.printf("Title: %s | Genre: %s\n", serie.getTitle(), serie.getGenre()));
        System.out.println();
    }
}
