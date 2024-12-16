package org.example.wmsmvp.product.feature;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.wmsmvp.product.domain.Category;
import org.example.wmsmvp.product.domain.ProductRepository;
import org.example.wmsmvp.product.domain.TemperatureZone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisterProductTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        //포트 설정
        if (RestAssured.UNDEFINED_PORT == RestAssured.port) {
            RestAssured.port = port;
        }
    }

    @Test
    @DisplayName("상품을 등록한다.")
    void registerProduct() throws Exception {

        //given
        final String name = "name";
        final String code = "code";
        final String description = "description";
        final String brand = "brand";
        final String maker = "maker";
        final String origin = "origin";
        final Long weightInGrams = 1000L;
        final Long widthInMillimeters = 100L;
        final Long heightInMillimeters = 100L;
        final Long lengthInMillimeters = 100L;

        RegisterProduct.Request request = new RegisterProduct.Request(
                name,
                code,
                description,
                brand,
                maker,
                origin,
                Category.ELECTRONICS,
                TemperatureZone.ROOM_TEMPERATURE,
                weightInGrams, //그램
                widthInMillimeters, //너비 mm
                heightInMillimeters, //높이 mm
                lengthInMillimeters //길이 mm
        );

        //when
//        registerProduct.request(request);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/products")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
        //then

        assertThat(productRepository.findAll()).hasSize(1);
    }

}
