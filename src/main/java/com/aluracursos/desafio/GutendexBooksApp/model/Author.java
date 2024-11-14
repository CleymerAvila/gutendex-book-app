package com.aluracursos.desafio.GutendexBooksApp.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private int birthYear;
    private int deathYear;
    @ManyToMany(mappedBy = "authors") // `mappedBy` indica que esta es la parte inversa de la relación
    private List<Book> books;

    public Author(){}

    public Author(AuthorData a){
        this.name = a.name();
        this.birthYear = Optional.of(a.birthYear()).orElse(0);
        this.deathYear = Optional.of(a.deathYear()).orElse(0);
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birhtYear) {
        this.birthYear = birhtYear;
    }

    public int getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(int deathYear) {
        this.deathYear = deathYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books =books;
    }

    @Override
    public String toString() {
        return "Nombre: "+ this.name;
    }

}
