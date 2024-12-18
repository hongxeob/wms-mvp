package org.example.wmsmvp.product.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ProductRepository extends JpaRepository<Product, Long> {
    default Product getBy(Long productNo) {
        return findById(productNo)
                .orElseThrow(() -> new IllegalArgumentException(
                        "상품이 존재하지 않습니다 .%d".formatted(productNo)));
    }
}
