package com.aluracursos.desafio.GutendexBooksApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements IConvertData {
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public <T> T getData(String json, Class<T> modelClass) throws Exception {
        try {
            return objectMapper.readValue(json, modelClass);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
