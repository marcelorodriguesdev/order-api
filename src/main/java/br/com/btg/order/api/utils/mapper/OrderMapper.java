package br.com.btg.order.api.utils.mapper;

import br.com.btg.order.infra.database.model.OrderModel;
import br.com.btg.order.api.response.ItemResponse;
import br.com.btg.order.api.response.OrderResponse;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {

    @BeforeMapping
    protected void setarAtributosDistintosModelToResponse(OrderModel orderModel, @MappingTarget OrderResponse.OrderResponseBuilder pedidoResponse) {
        List<ItemResponse> itensResponse = new ArrayList<>();
        orderModel.getItems().forEach(item ->
                itensResponse.add(ItemResponse.builder()
                        .price(BigDecimal.valueOf(item.getProduct().getPrice()))
                        .quantity(item.getQuantity())
                        .product(item.getProduct().getName())
                        .build())
        );
        pedidoResponse.itens(itensResponse);
        pedidoResponse.codigoCliente(orderModel.getCustomer().getId());
    }

    @Mapping(target = "itens", ignore = true)
    public abstract OrderResponse modelToResponse(OrderModel orderModel);

    @AfterMapping
    protected OrderResponse arredondarValorParaDuasCasasDecimaisModelToResponse(@MappingTarget OrderResponse.OrderResponseBuilder pedidoResponseBuilder) {
        OrderResponse orderResponse = pedidoResponseBuilder.build();
        orderResponse.getItens().forEach(item -> item.setPrice(item.getPrice().setScale(2, RoundingMode.CEILING)));
        return orderResponse;
    }

}
