package org.example.wmsmvp.inboud.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InboundTest {

    @Test
    @DisplayName("입고를 승인한다.")
    void confirmed() throws Exception {

        //given
        final Inbound inbound = new Inbound();
        final InboundStatus beforeStatus = inbound.getStatus();

        //when
        inbound.confirmed();

        //then
        assertThat(beforeStatus).isEqualTo(InboundStatus.REQUESTED);
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.CONFIRMED);
    }

    @Test
    @DisplayName("입고를 승인한다. - 입고 상태가 요청이 아닌 경우 예외 발생")
    void fail_invalid_status_confirmed() throws Exception {

        //given
        final Inbound inbound = new Inbound();

        //when -> then
        inbound.confirmed();
        assertThatThrownBy(() -> {
            inbound.confirmed();
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("입고 요청 상태가 아닙니다.");
    }
}
