package org.example.wmsmvp.inboud.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

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

    @Test
    @DisplayName("LPN 등록 - 성공")
    void registerLPN() throws Exception {

        //given
        final Inbound inbound = InboundFixture.anInboundWithConfirmed().build();

        final long inboundItemNo = 1L;
        final LocalDateTime expirationAt = LocalDateTime.now().plusDays(1);
        final String lpnBarcode = "LPN-1234";

        //when
        inbound.registerLPN(inboundItemNo, lpnBarcode, expirationAt);

        //then
        final InboundItem inboundItem = inbound.testingGetInboundItemBy(inboundItemNo);
        final List<LPN> lpns = inboundItem.getLpnList();
        assertThat(lpns).hasSize(1);
    }

    @Test
    @DisplayName("LPN 등록 - 실패 - 입고 확정 상태가 아님.")
    void fail_invalid_status_registerLPN() throws Exception {

        //given
        final Inbound inbound = InboundFixture.anInbound().build();

        final long inboundItemNo = 1L;
        final LocalDateTime expirationAt = LocalDateTime.now().plusDays(1);
        final String lpnBarcode = "LPN-1234";

        //when -> then
        assertThatThrownBy(() -> {
            inbound.registerLPN(inboundItemNo, lpnBarcode, expirationAt);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("입고 확정 상태가 아닙니다.");
    }

    @Test
    @DisplayName("LPN 등록 - 실패 - 유통기한이 현재 시간보다 이전인 경우")
    void fail_invalid_expiration_date_registerLPN() throws Exception {

        //given
        final Inbound inbound = InboundFixture.anInboundWithConfirmed().build();

        final long inboundItemNo = 1L;
        final LocalDateTime expirationAt = LocalDateTime.now().minusDays(1);
        final String lpnBarcode = "LPN-1234";

        //when -> then
        assertThatThrownBy(() -> {
            inbound.registerLPN(inboundItemNo, lpnBarcode, expirationAt);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("유통기한은 현재 시간보다 이후여야 합니다.");
    }
}
