package org.example.wmsmvp.inboud.feature;

import org.example.wmsmvp.inboud.domain.Inbound;
import org.example.wmsmvp.inboud.domain.InboundRepository;

class ConfirmInbound {

    private final InboundRepository inboundRepository;

    ConfirmInbound(final InboundRepository inboundRepository) {
        this.inboundRepository = inboundRepository;
    }

    public void request(final Long inboundNo) {
        final Inbound inbound = inboundRepository.getBy(inboundNo);

        inbound.confirmed();
    }

}
