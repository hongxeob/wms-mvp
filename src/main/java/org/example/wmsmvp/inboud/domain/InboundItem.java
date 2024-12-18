package org.example.wmsmvp.inboud.domain;

import org.example.wmsmvp.product.domain.Product;
import org.springframework.util.Assert;

public class InboundItem {
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
