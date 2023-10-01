package br.com.btg.order.infra.database.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_order")
public class OrderModel {
    @Id
    @Column(name = "id_order")
    private Long codigoPedido;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer")
    private CustomerModel customer;

    @Column(name = "data_hora_pedido")
    private LocalDateTime dataHoraPedido;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ItemModel> items;
}
