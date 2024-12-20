package org.example.wmsmvp.inboud.feature;

import org.example.wmsmvp.inboud.domain.Inbound;
import org.example.wmsmvp.inboud.domain.InboundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConfirmInboundTest {


    private ConfirmInbound confirmInbound;
    private InboundRepository inboundRepository;

    @BeforeEach
    void setUp() {
        confirmInbound = new ConfirmInbound();
    }

    @Test
    @DisplayName("입고를 승인한다.")
    void confirmInbound() throws Exception {
        //given
        Long inboundNo = 1L;

        //when
        confirmInbound.request(inboundNo);

        //then
//        assertThat(inboundRepository.findById(inboundNo).get().getStatus()).isEqualTo("");

    }

    private class ConfirmInbound {
        public void request(final Long inboundNo) {
            final Inbound inbound = inboundRepository.findById(inboundNo).orElseThrow();

            inbound.confirmed();
        }
    }
}
