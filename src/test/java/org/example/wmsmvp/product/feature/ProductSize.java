package org.example.wmsmvp.product.feature;

import org.springframework.util.Assert;

public class ProductSize {
    private final Long widthInMillimeters;
    private final Long heightInMillimeters;
    private final Long lengthInMillimeters;

    public ProductSize(Long widthInMillimeters, Long heightInMillimeters, Long lengthInMillimeters) {
        validateProductSize(widthInMillimeters, heightInMillimeters, lengthInMillimeters);
        this.widthInMillimeters = widthInMillimeters;
        this.heightInMillimeters = heightInMillimeters;
        this.lengthInMillimeters = lengthInMillimeters;
    }

    private void validateProductSize(Long widthInMillimeters, Long heightInMillimeters, Long lengthInMillimeters) {
        Assert.notNull(widthInMillimeters, "너비는 필수입니다.");
        Assert.isTrue(widthInMillimeters > 0, "너비는 0보다 커야 합니다.");

        Assert.notNull(heightInMillimeters, "높이는 필수입니다.");
        Assert.isTrue(heightInMillimeters > 0, "높이는 0보다 커야 합니다.");

        Assert.notNull(lengthInMillimeters, "길이는 필수입니다.");
        Assert.isTrue(lengthInMillimeters > 0, "길이는 0보다 커야 합니다.");
    }
}
