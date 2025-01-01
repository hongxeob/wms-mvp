package org.example.wmsmvp.inboud.feature;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.wmsmvp.inboud.domain.Inbound;
import org.example.wmsmvp.inboud.domain.InboundRepository;
import org.example.wmsmvp.inboud.domain.LPNRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class RegisterLPN {
    private final InboundRepository inboundRepository;
    private final LPNRepository lpnRepository;

    RegisterLPN(final InboundRepository inboundRepository, final LPNRepository lpnRepository) {
        this.inboundRepository = inboundRepository;
        this.lpnRepository = lpnRepository;
    }

    @Transactional
    @PostMapping("/inbounds/inbound-items/{inboundItemNo}/lpns")
    public void request(
            @PathVariable final Long inboundItemNo,
            @RequestBody @Valid final Request request
    ) {
        validateAlreadyExistsLPNBarcode(request.lpnBarcode);

        final Inbound inbound = inboundRepository.getInboundItemNo(inboundItemNo);

        inbound.registerLPN(inboundItemNo, request.lpnBarcode, request.expirationAt);
    }

    private void validateAlreadyExistsLPNBarcode(final String lpnBarcode) {
        lpnRepository.findByLpnBarcode(lpnBarcode).ifPresent(lpn -> {
            throw new LPNBarcodeAlreadyExistsException(lpnBarcode);
        });
    }

    public record Request(
            @NotBlank(message = "LPN 바코드는 필수입니다.")
            String lpnBarcode,
            @NotNull(message = "유통기한은 필수입니다.")
            LocalDateTime expirationAt
    ) {
    }
}
