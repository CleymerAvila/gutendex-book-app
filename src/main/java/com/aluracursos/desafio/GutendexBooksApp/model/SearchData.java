package com.aluracursos.desafio.GutendexBooksApp.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record SearchData(@JsonAlias("count") int numberResults
                        , @JsonAlias("next") String nextPage
                        , @JsonAlias("previous") String previousPage
                        , @JsonAlias("results")List<BookData> bookResults) {
}
