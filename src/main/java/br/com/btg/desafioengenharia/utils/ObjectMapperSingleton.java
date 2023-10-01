package br.com.btg.desafioengenharia.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;

@Getter
public class ObjectMapperSingleton {

    private static ObjectMapper instance = null;

    private ObjectMapperSingleton() {
    }

    public static ObjectMapper getInstance() {
        if (instance == null) {
            instance = new ObjectMapper();
            instance.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        }
        return instance;
    }
}
