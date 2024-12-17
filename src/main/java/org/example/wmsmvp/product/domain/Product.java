package org.example.wmsmvp.product.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

@Entity
@Comment("상품")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_no")
    @Comment("상품 번호")
    private Long productNo;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, updatable = true)
    private String code;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String maker;
    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "temperature_zone", nullable = false)
    @Enumerated(EnumType.STRING)
    private TemperatureZone temperatureZone;
    @Column(nullable = false)
    private Long weightInGrams;

    @Embedded
    private ProductSize productSize;

    public Product(
            String name,
            String code,
            String description,
            String brand,
            String maker,
            String origin,
            Category category,
            TemperatureZone temperatureZone,
            Long weightInGrams,
            ProductSize productSize) {
        validateConstructor(name, code, description, brand, maker, origin, category, temperatureZone, weightInGrams, productSize);
        this.name = name;
        this.code = code;
        this.description = description;
        this.brand = brand;
        this.maker = maker;
        this.origin = origin;
        this.category = category;
        this.temperatureZone = temperatureZone;
        this.weightInGrams = weightInGrams;
        this.productSize = productSize;
    }

    private void validateConstructor(String name, String code, String description, String brand, String maker, String origin, Category category, TemperatureZone temperatureZone, Long weightInGrams, ProductSize productSize) {
        Assert.hasText(name, "상품명은 필수입니다.");
        Assert.hasText(code, "상품 코드는 필수입니다.");
        Assert.hasText(description, "상품 설명은 필수입니다.");
        Assert.hasText(brand, "브랜드명은 필수입니다.");
        Assert.hasText(maker, "제조사는 필수입니다.");
        Assert.hasText(origin, "원산지는 필수입니다.");

        Assert.notNull(category, "카테고리는 필수입니다.");
        Assert.notNull(temperatureZone, "보관 온도는 필수입니다.");

        Assert.notNull(weightInGrams, "무게는 필수입니다.");
        Assert.isTrue(weightInGrams > 0, "무게는 0보다 커야 합니다.");
        Assert.notNull(productSize, "상품 크기는 필수 입니다.");
    }
}
