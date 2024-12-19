package org.example.wmsmvp.inboud.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.wmsmvp.product.domain.Product;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

@Entity
@Table(name = "inbound_item")
@Comment("입고 상품")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InboundItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("입고 상품 번호")
    @Column(name = "inbound_item_no")
    @Getter(AccessLevel.PROTECTED)
    private Long inboundItemNo;

    @Comment("상품")
    @JoinColumn(name = "product_no", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(name = "quantity", nullable = false)
    @Comment("수량")
    private Long quantity;

    @Column(name = "unit_price", nullable = false)
    @Comment("단가")
    private Long unitPrice;

    @Column(name = "description", nullable = false)
    @Comment("상품설명")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inbound_no", nullable = false)
    @Comment("입고 번호")
    private Inbound inbound;

    public InboundItem(Product product, Long quantity, Long unitPrice, String description) {
        validateConstructor(product, quantity, unitPrice, description);
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.description = description;
    }

    public void assignInbound(final Inbound inbound) {
        Assert.notNull(inbound, "입고는 필수입니다.");
        this.inbound = inbound;
    }

    private void validateConstructor(Product product, Long quantity, Long unitPrice, String description) {
        Assert.notNull(product, "상품 정보는 필수입니다.");
        Assert.notNull(quantity, "수량은 필수입니다.");
        Assert.isTrue(quantity > 0, "수량은 0보다 커야 합니다.");
        Assert.notNull(unitPrice, "단가는 필수입니다.");
        Assert.isTrue(unitPrice >= 0, "단가는 0 이상이어야 합니다.");
        Assert.hasText(description, "설명은 필수입니다.");
    }
}
