package org.example.wmsmvp.inboud.feature;

import org.example.wmsmvp.inboud.domain.Inbound;
import org.example.wmsmvp.inboud.domain.InboundRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Component
class RegisterLPN {
    private final InboundRepository inboundRepository;

    RegisterLPN(final InboundRepository inboundRepository) {
        this.inboundRepository = inboundRepository;
    }

    @Transactional
    public void request(final Request request) {
        final Inbound inbound = inboundRepository.getInboundItemNo(request.inboundItemNo);

        inbound.registerLPN(request.inboundItemNo, request.lpnBarcode, request.expirationAt);
    }

    public record Request(
            Long inboundItemNo,
            String lpnBarcode,
            LocalDateTime expirationAt
    ) {
        public Request {
            Assert.notNull(inboundItemNo, "LPN 등록 요청에는 inboundItemNo가 필요합니다.");
            Assert.hasText(lpnBarcode, "LPN 등록 요청에는 lpnBarcode가 필요합니다.");
            Assert.notNull(expirationAt, "LPN 등록 요청에는 expirationAt이 필요합니다.");
        }
    }
}
