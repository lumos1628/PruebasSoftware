package com.tecsup.minishop.dto;

import com.tecsup.minishop.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductRequestTest {

    @Test
    void toProduct_copiaLosCamposYNoAsignaId() {
        // Arrange
        ProductRequest request = new ProductRequest("Mouse", 50.0, 5);
        // Act
        Product product = request.toProduct();
        // Assert
        assertNull(product.getId());
        assertEquals("Mouse", product.getName());
        assertEquals(50.0, product.getPrice());
        assertEquals(5, product.getStock());
    }
}