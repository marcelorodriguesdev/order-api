package br.com.btg.order.api.utils.mapper;

import br.com.btg.order.infra.database.model.PedidoModel;
import br.com.btg.order.api.response.ItemPedidoResponse;
import br.com.btg.order.api.response.PedidoResponse;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PedidoMapper {

    @BeforeMapping
    protected void setarAtributosDistintosModelToResponse(PedidoModel pedidoModel, @MappingTarget PedidoResponse.PedidoResponseBuilder pedidoResponse) {
        List<ItemPedidoResponse> itensResponse = new ArrayList<>();
        pedidoModel.getItems().forEach(item ->
                itensResponse.add(ItemPedidoResponse.builder()
                        .preco(BigDecimal.valueOf(item.getProduct().getPrice()))
                        .quantidade(item.getQuantity())
                        .produto(item.getProduct().getName())
                        .build())
        );
        pedidoResponse.itens(itensResponse);
        pedidoResponse.codigoCliente(pedidoModel.getCustomer().getId());
    }

    @Mapping(target = "itens", ignore = true)
    public abstract PedidoResponse modelToResponse(PedidoModel pedidoModel);

    @AfterMapping
    protected PedidoResponse arredondarValorParaDuasCasasDecimaisModelToResponse(@MappingTarget PedidoResponse.PedidoResponseBuilder pedidoResponseBuilder) {
        PedidoResponse pedidoResponse = pedidoResponseBuilder.build();
        pedidoResponse.getItens().forEach(item -> item.setPreco(item.getPreco().setScale(2, RoundingMode.CEILING)));
        return pedidoResponse;
    }

}
