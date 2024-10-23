package com.aluracursos.desafio.GutendexBooksApp.model;

public class Author {
    private String name;
    private int ageOfDeath;

    public Author(AuthorData a){
        this.name = a.name();
        this.ageOfDeath = a.deathYear() - a.birthYear();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAgeOfDeath() {
        return ageOfDeath;
    }

    @Override
    public String toString() {
        return "Nombre autor: "+ this.name+", Edad Fallecimiento: "+ this.ageOfDeath;
    }
}
