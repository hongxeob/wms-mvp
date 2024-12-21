package org.example.wmsmvp.inboud.feature;

import org.example.wmsmvp.inboud.domain.Inbound;
import org.example.wmsmvp.inboud.domain.InboundItem;
import org.example.wmsmvp.inboud.domain.InboundRepository;
import org.example.wmsmvp.inboud.domain.InboundStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        final Inbound inbound = new Inbound(
                "상품명",
                "상품설명",
                LocalDateTime.now(),
                LocalDateTime.now(),
                List.of(
                        new InboundItem(
                                ProductFixture.aProduct().build(),
                                1L,
                                1500L,
                                "설명"
                        )
                )
        );
        Mockito.when(inboundRepository.findById(inboundNo))
                .thenReturn(Optional.of(inbound));

        //when
        confirmInbound.request(inboundNo);

        //then
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.CONFIRMED);

    }

    private class ConfirmInbound {

        private final InboundRepository inboundRepository;

        private ConfirmInbound(final InboundRepository inboundRepository) {
            this.inboundRepository = inboundRepository;
        }

        public void request(final Long inboundNo) {
            final Inbound inbound = inboundRepository.findById(inboundNo).orElseThrow();

            inbound.confirmed();
        }
    }
}
