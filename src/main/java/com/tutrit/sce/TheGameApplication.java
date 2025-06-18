package com.tutrit.sce;

import com.tutrit.sce.repository.CountryRepository;
import com.tutrit.sce.repository.GameRepository;
import com.tutrit.sce.service.GameEngine;
import com.tutrit.sce.service.ScoreBoard;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class TheGameApplication {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = SpringApplication.run(TheGameApplication.class, args);
		ScoreBoard scoreBoard = ctx.getBean(ScoreBoard.class);
		CountryRepository countryRepository = ctx.getBean(CountryRepository.class);
		GameRepository gameRepository = ctx.getBean(GameRepository.class);

		try (ExecutorService executorService = Executors.newFixedThreadPool(4)) {
			// Submit 10 game tasks
			for (int i = 0; i < 10; i++) {
				GameEngine engine = new GameEngine(countryRepository, gameRepository, scoreBoard);
				executorService.submit(engine::run);
				Thread.sleep(500);
			}
			executorService.shutdown();
		}
	}
}
