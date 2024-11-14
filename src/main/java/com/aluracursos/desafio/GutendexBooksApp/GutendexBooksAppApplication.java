package com.aluracursos.desafio.GutendexBooksApp;

import com.aluracursos.desafio.GutendexBooksApp.main.Main;
import com.aluracursos.desafio.GutendexBooksApp.service.AuthorService;
import com.aluracursos.desafio.GutendexBooksApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GutendexBooksAppApplication implements CommandLineRunner {

	@Autowired
	private BookService bookService;
	@Autowired
	private AuthorService authorService;

	public static void main(String[] args) {
		SpringApplication.run(GutendexBooksAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(bookService, authorService);

		main.initApp();
	}
}
