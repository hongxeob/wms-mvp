package org.example.wmsmvp.inboud.feature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

public class RegisterInboundTest {

    private RegisterInbound registerInbound;

    @BeforeEach
    void setUp() {
        registerInbound = new RegisterInbound();
    }

    @Test
    @DisplayName("입고를 등록한다.")
    void testRegisterInbound() {
        LocalDateTime orderRequestedAt = LocalDateTime.now();
        LocalDateTime estimatedArrivalAt = LocalDateTime.now().plusDays(1);
        Long productNo = 1L;
        Long quantity = 1L;
        long unitPrice = 1500L;
        RegisterInbound.Request.Item inboundItem = new RegisterInbound.Request.Item(
                productNo,
                quantity,
                unitPrice,
                "description");
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
        public void request(Request request) {
            throw new UnsupportedOperationException("Not supported yet.");
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
                    Long quantity,
                    long unitPrice,
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

    }
}
