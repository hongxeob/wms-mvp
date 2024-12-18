package org.example.wmsmvp.inboud.feature;

import org.example.wmsmvp.product.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RegisterInboundTest {

    private RegisterInbound registerInbound;
    private RegisterInbound.InboundRepository inboundRepository;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        inboundRepository = new RegisterInbound.InboundRepository();
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

    public class RegisterInbound {
        private final ProductRepository productRepository;
        private final InboundRepository inboundRepository;

        public RegisterInbound(final ProductRepository productRepository, final InboundRepository inboundRepository) {
            this.productRepository = productRepository;
            this.inboundRepository = inboundRepository;
        }

        public void request(Request request) {
            final List<InboundItem> inboundItems = request.inboundItems.stream()
                    .map(item ->
                            new InboundItem(productRepository.findById(item.productNo).orElseThrow(),
                                    item.quantity,
                                    item.unitPrice,
                                    item.description
                            ))
                    .toList();

            final Inbound inbound = new Inbound(
                    request.title,
                    request.description,
                    request.orderRequestedAt,
                    request.estimatedArrivalAt,
                    inboundItems
            );
            inboundRepository.save(inbound);
        }

        public record Request(
                String title,
                String content,
                String description,
                LocalDateTime orderRequestedAt,
                LocalDateTime estimatedArrivalAt,
                List<Item> inboundItems
        ) {
            public Request {
                Assert.hasText(title, "입고 제목은 필수입니다.");
                Assert.hasText(content, "입고 내용은 필수입니다.");
                Assert.hasText(description, "입고 설명은 필수입니다.");
                Assert.notNull(orderRequestedAt, "주문 요청 시간은 필수입니다.");
                Assert.notNull(estimatedArrivalAt, "예상 도착 시간은 필수입니다.");
                Assert.notEmpty(inboundItems, "입고 품목은 최소 1개 이상이어야 합니다.");
            }

            public record Item(
                    Long productNo,
                    Long quantity, Long unitPrice,
                    String description
            ) {
                public Item {
                    Assert.notNull(productNo, "상품 번호는 필수입니다.");
                    Assert.notNull(quantity, "수량은 필수입니다.");
                    Assert.isTrue(quantity > 0, "수량은 0보다 커야 합니다.");
                    Assert.isTrue(unitPrice >= 0, "단가는 0 이상이어야 합니다.");
                    Assert.hasText(description, "설명은 필수입니다.");
                }
            }
        }

        private class InboundItem {
            private Product product;
            private Long quantity;
            private Long unitPrice;
            private String description;

            public InboundItem(Product product, Long quantity, Long unitPrice, String description) {
                validateConstructor(product, quantity, unitPrice, description);
                this.product = product;
                this.quantity = quantity;
                this.unitPrice = unitPrice;
                this.description = description;
            }

            private static void validateConstructor(Product product, Long quantity, Long unitPrice, String description) {
                Assert.notNull(product, "상품 정보는 필수입니다.");
                Assert.notNull(quantity, "수량은 필수입니다.");
                Assert.isTrue(quantity > 0, "수량은 0보다 커야 합니다.");
                Assert.notNull(unitPrice, "단가는 필수입니다.");
                Assert.isTrue(unitPrice >= 0, "단가는 0 이상이어야 합니다.");
                Assert.hasText(description, "설명은 필수입니다.");
            }
        }

        private class Inbound {
            private Long id;
            private final String title;
            private final String description;
            private final LocalDateTime orderRequestedAt;
            private final LocalDateTime estimatedArrivalAt;
            private final List<InboundItem> inboundItems;

            public Inbound(
                    final String title,
                    final String description,
                    final LocalDateTime orderRequestedAt,
                    final LocalDateTime estimatedArrivalAt,
                    final List<InboundItem> inboundItems) {
                validateConstructor(title, description, orderRequestedAt, estimatedArrivalAt, inboundItems);
                this.title = title;
                this.description = description;
                this.orderRequestedAt = orderRequestedAt;
                this.estimatedArrivalAt = estimatedArrivalAt;
                this.inboundItems = inboundItems;
            }

            private static void validateConstructor(final String title, final String description, final LocalDateTime orderRequestedAt, final LocalDateTime estimatedArrivalAt, final List<InboundItem> inboundItems) {
                Assert.hasText(title, "입고 제목은 필수입니다.");
                Assert.hasText(description, "입고 설명은 필수입니다.");
                Assert.notNull(orderRequestedAt, "주문 요청 시간은 필수입니다.");
                Assert.notNull(estimatedArrivalAt, "예상 도착 시간은 필수입니다.");
                Assert.isTrue(orderRequestedAt.isBefore(estimatedArrivalAt),
                        "예상 도착 시간은 주문 요청 시간보다 이후여야 합니다.");
                Assert.notNull(inboundItems, "입고 품목 목록은 필수입니다.");
                Assert.notEmpty(inboundItems, "입고 품목은 최소 1개 이상이어야 합니다.");
            }

            public void assignId(final Long id) {
                this.id = id;
            }

            public Long getId() {
                return id;
            }
        }

        private static class InboundRepository {
            private final Map<Long, Inbound> inbounds = new HashMap<>();
            private Long sequence = 1L;


            public void save(final Inbound inbound) {
                inbound.assignId(sequence++);
                inbounds.put(inbound.getId(), inbound);
            }
        }
    }
}
