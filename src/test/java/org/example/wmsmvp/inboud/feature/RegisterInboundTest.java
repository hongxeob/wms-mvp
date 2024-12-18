package org.example.wmsmvp.inboud.feature;

import org.example.wmsmvp.inboud.domain.InboundRepository;
import org.example.wmsmvp.product.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class RegisterInboundTest {

    private RegisterInbound registerInbound;
    private InboundRepository inboundRepository;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        inboundRepository = new InboundRepository();
        registerInbound = new RegisterInbound(productRepository, inboundRepository);
    }

    @Test
    @DisplayName("입고를 등록한다.")
    void testRegisterInbound() {
        final Product product = new Product(
                "name",
                "code",
                "description",
                "brand",
                "maker",
                "origin",
                Category.ELECTRONICS,
                TemperatureZone.ROOM_TEMPERATURE,
                1000L,
                new ProductSize(
                        100L,
                        100L,
                        100L
                ));
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));

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
        registerInbound.request(request);
    }

}