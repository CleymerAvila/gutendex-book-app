package com.aluracursos.desafio.GutendexBooksApp.repository;


import com.aluracursos.desafio.GutendexBooksApp.model.Author;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    public Author findByName(String name);

    @Query("SELECT a FROM Author a WHERE a.birthYear <= :year AND (a.deathYear > :year OR a.deathYear IS NULL)")
    List<Author> findAuthorsWithAge(@Param("year") int year);

}
