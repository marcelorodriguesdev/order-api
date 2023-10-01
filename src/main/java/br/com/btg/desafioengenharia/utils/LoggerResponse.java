package br.com.btg.desafioengenharia.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoggerResponse {

    private String level;
    private String mensagem;
    private Object body;
    private LocalDateTime timestamp;
    private String aplicacao;

    @JsonIgnore
    private ObjectMapper mapper;

    public LoggerResponse(String mensagem, Object body) {
        this.mensagem = mensagem;
        this.body = body;
        this.timestamp = LocalDateTime.now();
        this.aplicacao = "desafioengenharia";
        this.mapper = ObjectMapperSingleton.getInstance();
    }

    @SneakyThrows
    public String toJson() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.writeValueAsString(this);
    }

}
