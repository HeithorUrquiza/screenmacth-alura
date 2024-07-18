package com.alura.screenmatch;

import com.alura.screenmatch.models.SerieData;
import com.alura.screenmatch.services.ApiConsumer;
import com.alura.screenmatch.services.DataConversor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Dotenv dotenv = Dotenv.load();
		final String API_KEY = dotenv.get("IMDB_API_KEY");
		String urlAddress = String.format("https://www.omdbapi.com/?t=game+of+thrones&apikey=%s", API_KEY);

		ApiConsumer apiConsumer = new ApiConsumer();
		String json = apiConsumer.getData(urlAddress);

		DataConversor dataConversor = new DataConversor();
		SerieData serieData = dataConversor.getData(json, SerieData.class);
		System.out.println(serieData);
	}
}
