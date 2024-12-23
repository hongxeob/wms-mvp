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

public class RejectInboundTest {

    private RejectInbound rejectInbound;
    private InboundRepository inboundRepository;

    @BeforeEach
    void setUp() {
        inboundRepository = Mockito.mock(InboundRepository.class);
        rejectInbound = new RejectInbound(inboundRepository);
    }

    @Test
    @DisplayName("입고를 거부할 수 있다.")
    void rejectInboundTest() throws Exception {

        //given
        final Inbound inbound = InboundFixture.anInbound().build();
        final Long inboundNo = 1L;
        final String rejectionReason = "반려 사유";
        final RejectInbound.Request request = new RejectInbound.Request(rejectionReason);

        Mockito.when(inboundRepository.getById(inboundNo))
                .thenReturn(inbound);

        //when
        rejectInbound.request(inboundNo, request);

        //then
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.REJECTED);
    }

}
