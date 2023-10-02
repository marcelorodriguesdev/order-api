package br.com.btg.order.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ItemRequest implements Serializable {

    @JsonProperty("produto")
    private String product;

    @JsonProperty("quantidade")
    private long quantity;

    @JsonProperty("preco")
    private BigDecimal price;
}
