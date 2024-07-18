package com.alura.screenmatch.principal;

import com.alura.screenmatch.models.SeasonData;
import com.alura.screenmatch.models.SerieData;
import com.alura.screenmatch.services.ApiConsumer;
import com.alura.screenmatch.services.DataConversor;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ApiConsumer apiConsumer = new ApiConsumer();
    private DataConversor dataConversor = new DataConversor();
    private List<SeasonData> seasons = new ArrayList<>();

    // Constants
    private final Dotenv dotenv = Dotenv.load();
    private final String API_KEY = dotenv.get("IMDB_API_KEY");
    private final String BASE_URL = "https://www.omdbapi.com/?t=";

    public void showMenu() {
        System.out.print("Insert the series name: ");
        String serieName = scanner.nextLine();
        // Getting serie data
        String serieJson = apiConsumer.getData(
                BASE_URL +
                        serieName.replace(" ", "+") +
                        "&apiKey=" + API_KEY
        );
        SerieData serieData = dataConversor.getData(serieJson, SerieData.class);
        System.out.println(serieData);

        // Getting season data
        for (int i = 1; i <= serieData.totalSeasons(); i++) {
            String seasonJson = apiConsumer.getData(
                    BASE_URL +
                            serieName.replace(" ", "+") +
                            "&season=" + i +
                            "&apiKey=" + API_KEY
            );
            seasons.add(dataConversor.getData(seasonJson, SeasonData.class));
        }
        seasons.forEach(System.out::println);
    }
}
