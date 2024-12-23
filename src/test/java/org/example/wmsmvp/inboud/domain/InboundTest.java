package org.example.wmsmvp.inboud.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.wmsmvp.inboud.domain.InboundFixture.anInbound;

class InboundTest {

    @Test
    @DisplayName("입고를 승인한다.")
    void confirmed() throws Exception {

        //given
        final Inbound inbound = anInbound().build();
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
        final Inbound inbound = InboundFixture.anInboundWithConfirmed().build();

        //when -> then
        assertThatThrownBy(() -> {
            inbound.confirmed();
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("입고 요청 상태가 아닙니다.");
    }

    @Test
    @DisplayName("입고를 반려/거부하면 입고의 상태가 REJECTED가 된다.")
    void rejectTest() throws Exception {

        //given
        final Inbound inbound = anInbound().build();
        final InboundStatus beforeStatus = inbound.getStatus();

        //when
        final String rejectionReason = "반려 사유";
        inbound.reject(rejectionReason);

        //then
        assertThat(beforeStatus).isEqualTo(InboundStatus.REQUESTED);
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.REJECTED);
    }

    @Test
    @DisplayName("입고를 거부한다. - 입고 상태가 요청이 아닌 경우 예외 발생")
    void fail_invalid_status_rejected() throws Exception {

        //given
        final Inbound inbound = InboundFixture.anInboundWithConfirmed().build();
        final String rejectionReason = "반려 사유";


        //when -> then
        assertThatThrownBy(() -> {
            inbound.reject(rejectionReason);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("입고 요청 상태가 아닙니다.");
    }
}
