package org.example.wmsmvp.inboud.feature;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.wmsmvp.common.ApiTest;
import org.example.wmsmvp.inboud.domain.InboundRepository;
import org.example.wmsmvp.product.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterInboundTest extends ApiTest {

    @MockitoBean
    private ProductRepository productRepository;
    @Autowired
    private InboundRepository inboundRepository;

    @Test
    @DisplayName("입고를 등록한다.")
    void testRegisterInbound() {
        Mockito.when(productRepository.getBy(Mockito.anyLong()))
                .thenReturn(ProductFixture.aProduct().build());

        LocalDateTime orderRequestedAt = LocalDateTime.now();
        LocalDateTime estimatedArrivalAt = LocalDateTime.now().plusDays(1);
        Long productNo = 1L;
        Long quantity = 1L;
        Long unitPrice = 1500L;
        final String description = "description";

        RegisterInbound.Request.Item inboundItem = new RegisterInbound.Request.Item(
                productNo,
                quantity,
                unitPrice,
                description);

        List<RegisterInbound.Request.Item> inboundItems = List.of(inboundItem);

        RegisterInbound.Request request = new RegisterInbound.Request(
                "title",
                "content",
                "description",
                orderRequestedAt,
                estimatedArrivalAt,
                inboundItems);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/inbounds")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        assertThat(inboundRepository.findAll()).hasSize(1);
    }
}
