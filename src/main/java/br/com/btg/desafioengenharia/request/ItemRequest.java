package br.com.btg.desafioengenharia.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ItemRequest implements Serializable {

    @JsonProperty("produto")
    private String produto;

    @JsonProperty("quantidade")
    private long quantidade;

    @JsonProperty("preco")
    private BigDecimal preco;

    @Override
    public String toString() {
        return "{\"produto\":\"" + produto + "\""
                + ", \"quantidade\":\"" + quantidade + "\""
                + ", \"preco\":\"" + preco + "\""
                + "}";
    }
}