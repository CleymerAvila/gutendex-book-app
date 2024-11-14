package com.aluracursos.desafio.GutendexBooksApp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(@JsonAlias("title")String title
                        , @JsonAlias("id") int bookId
                        , @JsonAlias("authors") List<AuthorData> authors
                        , @JsonAlias("download_count") int downloadCounts
                        , @JsonAlias("languages") List<String> languages){
}
