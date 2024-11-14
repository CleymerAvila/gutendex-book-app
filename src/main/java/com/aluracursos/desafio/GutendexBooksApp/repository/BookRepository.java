package com.aluracursos.desafio.GutendexBooksApp.repository;

import com.aluracursos.desafio.GutendexBooksApp.model.Book;
import com.aluracursos.desafio.GutendexBooksApp.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByTitle(String title);

    List<Book> findByLanguage(Language language);
}
