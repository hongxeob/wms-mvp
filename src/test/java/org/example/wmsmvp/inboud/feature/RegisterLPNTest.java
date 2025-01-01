package org.example.wmsmvp.inboud.feature;

import org.example.wmsmvp.common.ApiTest;
import org.example.wmsmvp.common.Scenario;
import org.example.wmsmvp.inboud.domain.Inbound;
import org.example.wmsmvp.inboud.domain.InboundItem;
import org.example.wmsmvp.inboud.domain.InboundRepository;
import org.example.wmsmvp.inboud.domain.LPN;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterLPNTest extends ApiTest {

    @Autowired
    private RegisterLPN registerLPN;
    @Autowired
    private InboundRepository inboundRepository;

    @BeforeEach
    void setUpForRegisterLPN() {
        Scenario.registerProduct().request()
                .registerInbound().request()
                .confirmInbound().request();
    }

    @Test
    @DisplayName("LPN을 등록한다.")
    @Transactional
    void registerLPN() throws Exception {

        //given
        final Long inboundItemNo = 1L;

        //when
        Scenario.registerLPN().request();

        //then
        final Inbound inbound = inboundRepository.findByInboundItemNo(inboundItemNo).get();
        final InboundItem inboundItem = inbound.testingGetInboundItemBy(inboundItemNo);
        final List<LPN> lpnList = inboundItem.getLpnList();
        assertThat(lpnList.size()).isEqualTo(1);
    }

}
