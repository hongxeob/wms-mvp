package org.example.wmsmvp.product.feature;

import org.example.wmsmvp.product.domain.*;
import org.springframework.util.Assert;

public class RegisterProduct {
    private final ProductRepository productRepository;

    public RegisterProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void request(Request request) {
        Product product = request.toDomain();
        productRepository.save(product);
    }

    public record Request(
            String name,
            String code,
            String description,
            String brand,
            String maker,
            String origin,
            Category category,
            TemperatureZone temperatureZone,
            Long weightInGrams,
            Long widthInMillimeters,
            Long heightInMillimeters,
            Long lengthInMillimeters
    ) {
        public Request {
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

            Assert.notNull(widthInMillimeters, "너비는 필수입니다.");
            Assert.isTrue(widthInMillimeters > 0, "너비는 0보다 커야 합니다.");

            Assert.notNull(heightInMillimeters, "높이는 필수입니다.");
            Assert.isTrue(heightInMillimeters > 0, "높이는 0보다 커야 합니다.");

            Assert.notNull(lengthInMillimeters, "길이는 필수입니다.");
            Assert.isTrue(lengthInMillimeters > 0, "길이는 0보다 커야 합니다.");
        }

        public Product toDomain() {
            return new Product(
                    name,
                    code,
                    description,
                    brand,
                    maker,
                    origin,
                    category,
                    temperatureZone,
                    weightInGrams,
                    new ProductSize(widthInMillimeters, heightInMillimeters, lengthInMillimeters));
        }
    }
}