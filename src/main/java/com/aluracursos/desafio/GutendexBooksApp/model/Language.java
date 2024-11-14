package com.aluracursos.desafio.GutendexBooksApp.model;

public enum Language {
    ESPAÑOL("es"),
    INGLES("en"),
    ITALIANO("it"),
    PORTUGUES("pt"),
    RUSO("ru");

    private final String shortWord;

    Language(String language){
        this.shortWord = language;
    }

    public String getShortWord(){
        return shortWord;
    }

    public static Language fromString(String language){
        for (Language l : Language.values()){
            if (l.getShortWord().equals(language)){
                return l;
            }
        }
        return null;
    }
}
