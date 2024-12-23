package org.example.wmsmvp.inboud.feature;

import org.example.wmsmvp.common.ApiTest;
import org.example.wmsmvp.common.Scenario;
import org.example.wmsmvp.inboud.domain.Inbound;
import org.example.wmsmvp.inboud.domain.InboundRepository;
import org.example.wmsmvp.inboud.domain.InboundStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class RejectInboundTest extends ApiTest {

    @Autowired
    private InboundRepository inboundRepository;

    @Test
    @DisplayName("입고를 거부할 수 있다.")
    void rejectInboundTest() throws Exception {

        //given

        Scenario.registerProductApi().request()
                .registerInboundApi().request()
                .rejectInboundApi().request();

        //when
        final Inbound inbound = inboundRepository.getBy(1L);

        //then
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.REJECTED);
    }

}
