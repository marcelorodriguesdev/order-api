package br.com.btg.order.model;

import br.com.btg.order.infra.database.model.CustomerModel;
import br.com.btg.order.infra.database.model.ItemPedidoModel;
import br.com.btg.order.infra.database.model.PedidoModel;
import br.com.btg.order.infra.database.model.ProdutoModel;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ModelsTest {

    @Test
    public void testPedidoModel() throws Exception {
        assertNotNull(new PedidoModel());
        assertThat(ModelsTest.class, allOf(hasValidBeanConstructor(),
                hasValidBeanEquals(),
                hasValidBeanEqualsExcluding(),
                hasValidBeanEqualsFor(),
                hasValidBeanHashCode(),
                hasValidBeanHashCodeExcluding(),
                hasValidBeanHashCodeFor(),
                hasValidBeanToString(),
                hasValidBeanToStringExcluding(),
                hasValidBeanToStringFor(),
                hasValidGettersAndSetters(),
                hasValidGettersAndSettersExcluding(),
                hasValidGettersAndSettersFor()));
    }

    @Test
    public void testProdutoModel() throws Exception {
        assertNotNull(new ProdutoModel());
        assertThat(ModelsTest.class, allOf(hasValidBeanConstructor(),
                hasValidBeanEquals(),
                hasValidBeanEqualsExcluding(),
                hasValidBeanEqualsFor(),
                hasValidBeanHashCode(),
                hasValidBeanHashCodeExcluding(),
                hasValidBeanHashCodeFor(),
                hasValidBeanToString(),
                hasValidBeanToStringExcluding(),
                hasValidBeanToStringFor(),
                hasValidGettersAndSetters(),
                hasValidGettersAndSettersExcluding(),
                hasValidGettersAndSettersFor()));
    }

    @Test
    public void testItemPedidoModel() throws Exception {
        assertNotNull(new ItemPedidoModel());
        assertThat(ModelsTest.class, allOf(hasValidBeanConstructor(),
                hasValidBeanEquals(),
                hasValidBeanEqualsExcluding(),
                hasValidBeanEqualsFor(),
                hasValidBeanHashCode(),
                hasValidBeanHashCodeExcluding(),
                hasValidBeanHashCodeFor(),
                hasValidBeanToString(),
                hasValidBeanToStringExcluding(),
                hasValidBeanToStringFor(),
                hasValidGettersAndSetters(),
                hasValidGettersAndSettersExcluding(),
                hasValidGettersAndSettersFor()));
    }

    @Test
    public void testClienteModel() throws Exception {
        assertNotNull(new CustomerModel());
        assertThat(ModelsTest.class, allOf(hasValidBeanConstructor(),
                hasValidBeanEquals(),
                hasValidBeanEqualsExcluding(),
                hasValidBeanEqualsFor(),
                hasValidBeanHashCode(),
                hasValidBeanHashCodeExcluding(),
                hasValidBeanHashCodeFor(),
                hasValidBeanToString(),
                hasValidBeanToStringExcluding(),
                hasValidBeanToStringFor(),
                hasValidGettersAndSetters(),
                hasValidGettersAndSettersExcluding(),
                hasValidGettersAndSettersFor()));
    }

}