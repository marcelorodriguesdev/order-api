package br.com.btg.desafioengenharia.model;

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
        assertNotNull(new ClienteModel());
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