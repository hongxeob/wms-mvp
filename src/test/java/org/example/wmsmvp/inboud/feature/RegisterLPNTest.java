package org.example.wmsmvp.inboud.feature;

import org.example.wmsmvp.common.ApiTest;
import org.example.wmsmvp.common.Scenario;
import org.example.wmsmvp.inboud.domain.Inbound;
import org.example.wmsmvp.inboud.domain.InboundItem;
import org.example.wmsmvp.inboud.domain.InboundRepository;
import org.example.wmsmvp.inboud.domain.LPN;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterLPNTest extends ApiTest {

    @Autowired
    private RegisterLPN registerLPN;
    @Autowired
    private InboundRepository inboundRepository;

    @Test
    @DisplayName("LPN을 등록한다.")
    @Transactional
    void registerLPN() throws Exception {

        //given
        Scenario.registerProduct().request()
                .registerInbound().request()
                .confirmInbound().request();

        final Long inboundItemNo = 1L;
        final String lpnBarcode = "LPN-1234";
        final LocalDateTime expirationAt = LocalDateTime.now().plusDays(1);
        RegisterLPN.Request request = new RegisterLPN.Request(
                inboundItemNo,
                lpnBarcode,
                expirationAt
        );

        //when
        registerLPN.request(request);

        final Inbound inbound = inboundRepository.findByInboundItemNo(inboundItemNo).get();

        //then
        final InboundItem inboundItem = inbound.testingGetInboundItemBy(inboundItemNo);
        final List<LPN> lpnList = inboundItem.getLpnList();
        assertThat(lpnList.size()).isEqualTo(1);
    }

}
