package com.aluracursos.desafio.GutendexBooksApp;

import com.aluracursos.desafio.GutendexBooksApp.main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GutendexBooksAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GutendexBooksAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main();

		main.initApp();
	}
}
