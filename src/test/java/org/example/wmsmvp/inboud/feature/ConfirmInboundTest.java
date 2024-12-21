package org.example.wmsmvp.inboud.feature;

import org.example.wmsmvp.inboud.domain.Inbound;
import org.example.wmsmvp.inboud.domain.InboundFixture;
import org.example.wmsmvp.inboud.domain.InboundRepository;
import org.example.wmsmvp.inboud.domain.InboundStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfirmInboundTest {


    private ConfirmInbound confirmInbound;
    private InboundRepository inboundRepository;

    @BeforeEach
    void setUp() {
        inboundRepository = Mockito.mock(InboundRepository.class);
        confirmInbound = new ConfirmInbound(inboundRepository);
    }

    @Test
    @DisplayName("입고를 승인한다.")
    void confirmInbound() throws Exception {
        //given
        Long inboundNo = 1L;
        final Inbound inbound = InboundFixture.anInbound().build();
        Mockito.when(inboundRepository.getBy(inboundNo))
                .thenReturn(inbound);

        //when
        confirmInbound.request(inboundNo);

        //then
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.CONFIRMED);
    }
}
