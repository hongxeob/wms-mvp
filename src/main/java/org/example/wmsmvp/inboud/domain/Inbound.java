package org.example.wmsmvp.inboud.domain;

import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

public class Inbound {
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
