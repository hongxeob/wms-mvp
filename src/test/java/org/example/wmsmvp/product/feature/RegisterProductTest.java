package org.example.wmsmvp.product.feature;

import org.example.wmsmvp.common.ApiTest;
import org.example.wmsmvp.common.Scenario;
import org.example.wmsmvp.product.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


public class RegisterProductTest extends ApiTest {

    @Autowired
    private ProductRepository productRepository;


    @Test
    @DisplayName("상품을 등록한다.")
    void registerProduct() throws Exception {

        //given -> when
        Scenario.registerProduct().request();

        //then
        assertThat(productRepository.findAll()).hasSize(1);
    }
}
