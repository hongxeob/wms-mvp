package org.example.wmsmvp.inboud.feature;

import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.example.wmsmvp.inboud.domain.Inbound;
import org.example.wmsmvp.inboud.domain.InboundItem;
import org.example.wmsmvp.inboud.domain.InboundRepository;
import org.example.wmsmvp.product.domain.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RegisterInbound {
    private final ProductRepository productRepository;
    private final InboundRepository inboundRepository;

    @PostMapping("/inbounds")
    @ResponseStatus(HttpStatus.CREATED)
    public void request(@RequestBody Request request) {
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
            @NotBlank(message = "입고 제목은 필수 입니다.")
            String title,
            @NotBlank(message = "입고 내용은 필수입니다.")
            String content,
            @NotBlank(message = "입고 설명은 필수입니다.")
            String description,
            @NotNull(message = "주문 요청 시간은 필수입니다.")
            LocalDateTime orderRequestedAt,
            @NotNull(message = "예상 도착 시간은 필수입니다.")
            LocalDateTime estimatedArrivalAt,
            @NotEmpty(message = "입고 품목은 최소 1개 이상이어야 합니다.")
            List<Request.Item> inboundItems
    ) {
        public record Item(
                @NotNull(message = "상품 번호는 필수입니다.")
                Long productNo,
                @NotNull(message = "수량은 필수입니다.")
                @Min(value = 1, message = "수량은 0보다 커야 합니다.")
                Long quantity,
                @PositiveOrZero(message = "단가는 0 이상이어야 합니다.")
                Long unitPrice,
                @NotBlank(message = "설명은 필수입니다.")
                String description
        ) {
        }
    }

}
