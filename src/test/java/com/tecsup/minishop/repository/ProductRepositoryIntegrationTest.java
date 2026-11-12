package com.tecsup.minishop.repository;

import com.tecsup.minishop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    // PRUEBA 1: escribe aquí shouldSaveProductAndAssignId.
    @Test
    @DisplayName("Debe guardar un producto y asignarle ID automaticamente")
    void shouldSaveProductAndAssignId() {
        //preparar
        Product product = Product.builder()
                .name("Laptop Lenovo")
                .price(2500.00)
                .stock(10)
                .build();
        //Actuar
        Product saved = productRepository.save(product);

        //Assert
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Laptop Lenovo");
        assertThat(saved.getPrice()).isEqualTo(2500.00);
    }

    @Test
    @DisplayName("Debe encontrar un producto por ID existente")
    void shouldFindProductById() {
        // Arrange
        Product product = productRepository.save(
                Product.builder().name("Mouse Logitech").price(85.00).stock(50).build()
        );

        // Act
        Optional<Product> found = productRepository.findById(product.getId());

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Mouse Logitech");
    }
    @Test
    @DisplayName("Debe retornar vacío cuando el ID no existe")
    void shouldReturnEmptyWhenIdNotFound() {
        // Act
        Optional<Product> found = productRepository.findById(999L);

        // Assert
        assertThat(found).isEmpty();
    }
    @Test
    @DisplayName("Debe buscar productos por nombre ignorando mayúsculas")
    void shouldFindProductsByNameIgnoringCase() {
        // Arrange
        productRepository.save(Product.builder().name("Teclado Mecánico").price(150.00).stock(20).build());
        productRepository.save(Product.builder().name("Teclado Membrana").price(45.00).stock(30).build());
        productRepository.save(Product.builder().name("Monitor Dell").price(800.00).stock(5).build());

        // Act
        List<Product> result = productRepository.findByNameContainingIgnoreCase("teclado");

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).extracting(Product::getName)
                .containsExactlyInAnyOrder("Teclado Mecánico", "Teclado Membrana");
    }

}
