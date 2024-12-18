package org.example.wmsmvp.inboud.feature;

import org.example.wmsmvp.inboud.domain.Inbound;
import org.example.wmsmvp.inboud.domain.InboundItem;
import org.example.wmsmvp.inboud.domain.InboundRepository;
import org.example.wmsmvp.product.domain.ProductRepository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

public class RegisterInbound {
    private final ProductRepository productRepository;
    private final InboundRepository inboundRepository;

    public RegisterInbound(final ProductRepository productRepository, final InboundRepository inboundRepository) {
        this.productRepository = productRepository;
        this.inboundRepository = inboundRepository;
    }

    public void request(Request request) {
        inboundRepository.save(createInbound(request));
    }

    private Inbound createInbound(final Request request) {
        return new Inbound(
                request.title,
                request.description,
                request.orderRequestedAt,
                request.estimatedArrivalAt,
                mapToInboundItems(request)
        );
    }

    private List<InboundItem> mapToInboundItems(final Request request) {
        return request.inboundItems.stream()
                .map(this::newInboundItem)
                .toList();
    }

    private InboundItem newInboundItem(final Request.Item item) {
        return new InboundItem(productRepository.getBy(item.productNo),
                item.quantity,
                item.unitPrice,
                item.description
        );
    }

    public record Request(
            String title,
            String content,
            String description,
            LocalDateTime orderRequestedAt,
            LocalDateTime estimatedArrivalAt,
            List<Request.Item> inboundItems
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

}
