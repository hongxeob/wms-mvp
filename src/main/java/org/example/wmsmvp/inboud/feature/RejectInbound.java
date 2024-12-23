package org.example.wmsmvp.inboud.feature;

import org.example.wmsmvp.inboud.domain.Inbound;
import org.example.wmsmvp.inboud.domain.InboundRepository;

class RejectInbound {
    private final InboundRepository inboundRepository;

    RejectInbound(final InboundRepository inboundRepository) {
        this.inboundRepository = inboundRepository;
    }


    public void request(final Long inboundNo, final Request request) {
        final Inbound inbound = inboundRepository.getById(inboundNo);

        inbound.reject(request.rejectionReason);
    }

    public record Request(String rejectionReason) {
    }
}
