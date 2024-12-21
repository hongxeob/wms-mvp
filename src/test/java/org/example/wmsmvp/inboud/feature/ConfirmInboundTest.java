package org.example.wmsmvp.inboud.feature;

import io.restassured.RestAssured;
import org.example.wmsmvp.common.ApiTest;
import org.example.wmsmvp.common.Scenario;
import org.example.wmsmvp.inboud.domain.Inbound;
import org.example.wmsmvp.inboud.domain.InboundRepository;
import org.example.wmsmvp.inboud.domain.InboundStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfirmInboundTest extends ApiTest {
    @Autowired
    private InboundRepository inboundRepository;

    @Test
    @DisplayName("입고를 승인한다.")
    void confirmInbound() throws Exception {
        //given
        Scenario.registerProductApi().request()
                .registerInboundApi().request();
        Long inboundNo = 1L;

        //when
        RestAssured.given().log().all()
                .when().post("/inbounds/{inboundNo}/confirm", inboundNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        final Inbound inbound = inboundRepository.getBy(inboundNo);

        //then
        assertThat(inbound.getStatus()).isEqualTo(InboundStatus.CONFIRMED);
    }

}
