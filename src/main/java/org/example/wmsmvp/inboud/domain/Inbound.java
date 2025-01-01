package org.example.wmsmvp.inboud.domain;

import com.google.common.annotations.VisibleForTesting;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inbound")
@Comment("입고")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Inbound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inbound_no")
    @Comment("입고 번호")
    private Long inboundNo;

    @Column(name = "title", nullable = false)
    @Comment("입고명")
    private String title;

    @Column(name = "description", nullable = false)
    @Comment("입고설명")
    private String description;

    @Column(name = "order_requested_at", nullable = false)
    @Comment("입고요청일시")
    private LocalDateTime orderRequestedAt;

    @Column(name = "estimated_arrival_at", nullable = false)
    @Comment("입고예정일시")
    private LocalDateTime estimatedArrivalAt;

    @OneToMany(mappedBy = "inbound", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private final List<InboundItem> inboundItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Comment("입고 진행 상태")
    private InboundStatus status = InboundStatus.REQUESTED;

    @Comment("반려 사유")
    @Column(name = "rejection_reason")
    private String rejectionReason;

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
        for (InboundItem inboundItem : inboundItems) {
            this.inboundItems.add(inboundItem);
            inboundItem.assignInbound(this);
        }
    }

    @VisibleForTesting
    Inbound(
            final Long inboundNo,
            final String title,
            final String description,
            final LocalDateTime orderRequestedAt,
            final LocalDateTime estimatedArrivalAt,
            final List<InboundItem> inboundItems,
            final InboundStatus status
    ) {
        this(title, description, orderRequestedAt, estimatedArrivalAt, inboundItems);
        this.inboundNo = inboundNo;
        this.status = status;
    }

    public void confirmed() {
        validateConfirmStatus();
        status = InboundStatus.CONFIRMED;
    }

    public void reject(final String rejectionReason) {
        validateRejectStatus(rejectionReason);
        status = InboundStatus.REJECTED;
        this.rejectionReason = rejectionReason;
    }

    public void registerLPN(
            final Long inboundItemNo,
            final String lpnBarcode,
            final LocalDateTime expirationAt) {
        validateRegisterLPN(inboundItemNo, lpnBarcode, expirationAt);

        final InboundItem inboundItem = getInboundItemBy(inboundItemNo);
        inboundItem.registerLPN(lpnBarcode, expirationAt);
    }

    private void validateRegisterLPN(
            final Long inboundItemNo,
            final String lpnBarcode,
            final LocalDateTime expirationAt
    ) {
        if (status != InboundStatus.CONFIRMED) {
            throw new IllegalArgumentException("입고 확정 상태가 아닙니다.");
        }

        Assert.notNull(inboundItemNo, "입고 상품 번호는 필수입니다.");
        Assert.hasText(lpnBarcode, "LPN 바코드는 필수입니다.");
        Assert.notNull(expirationAt, "유통기한은 필수입니다.");

        if (expirationAt.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("유통기한은 현재 시간보다 이후여야 합니다.");
        }
        final InboundItem inboundItem = getInboundItemBy(inboundItemNo);
        final List<LPN> lpns = inboundItem.getLpnList();
    }

    private void validateRejectStatus(final String rejectionReason) {
        Assert.hasText(rejectionReason, "반려 사유는 필수입니다.");
        if (status != InboundStatus.REQUESTED) {
            throw new IllegalArgumentException("입고 요청 상태가 아닙니다.");
        }
    }

    private void validateConfirmStatus() {
        if (status != InboundStatus.REQUESTED) {
            throw new IllegalArgumentException("입고 요청 상태가 아닙니다.");
        }
    }

    private void validateConstructor(
            final String title,
            final String description,
            final LocalDateTime orderRequestedAt,
            final LocalDateTime estimatedArrivalAt,
            final List<InboundItem> inboundItems
    ) {
        Assert.hasText(title, "입고 제목은 필수입니다.");
        Assert.hasText(description, "입고 설명은 필수입니다.");
        Assert.notNull(orderRequestedAt, "주문 요청 시간은 필수입니다.");
        Assert.notNull(estimatedArrivalAt, "예상 도착 시간은 필수입니다.");
        Assert.isTrue(orderRequestedAt.isBefore(estimatedArrivalAt),
                "예상 도착 시간은 주문 요청 시간보다 이후여야 합니다.");
        Assert.notNull(inboundItems, "입고 품목 목록은 필수입니다.");
        Assert.notEmpty(inboundItems, "입고 품목은 최소 1개 이상이어야 합니다.");
    }

    private InboundItem getInboundItemBy(final Long inboundItemNo) {
        return inboundItems.stream()
                .filter(ii -> ii.getInboundItemNo().equals(inboundItemNo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 입고 상품이 없습니다 %d.".formatted(inboundItemNo)));
    }

    @VisibleForTesting
    public InboundItem testingGetInboundItemBy(final Long inboundItemNo) {
        return inboundItems.stream()
                .filter(ii -> ii.getInboundItemNo().equals(inboundItemNo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 입고 상품이 없습니다 %d.".formatted(inboundItemNo)));
    }
}
