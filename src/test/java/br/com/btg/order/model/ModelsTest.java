package br.com.btg.order.model;

import br.com.btg.order.infra.database.model.CustomerModel;
import br.com.btg.order.infra.database.model.ItemModel;
import br.com.btg.order.infra.database.model.OrderModel;
import br.com.btg.order.infra.database.model.ProductModel;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ModelsTest {

    @Test
    public void testOrderModel() throws Exception {
        assertNotNull(new OrderModel());
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
    public void testProductModel() throws Exception {
        assertNotNull(new ProductModel());
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
    public void testItemModel() throws Exception {
        assertNotNull(new ItemModel());
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
    public void testCustomerModel() throws Exception {
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
