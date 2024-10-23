package com.aluracursos.desafio.GutendexBooksApp.service;

public interface IConvertData {
    <T> T getData(String json, Class<T> modelClass) throws Exception;
}
