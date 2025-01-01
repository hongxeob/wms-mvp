package org.example.wmsmvp.inboud.feature;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.wmsmvp.common.ApiTest;
import org.example.wmsmvp.common.Scenario;
import org.example.wmsmvp.inboud.domain.Inbound;
import org.example.wmsmvp.inboud.domain.InboundItem;
import org.example.wmsmvp.inboud.domain.InboundRepository;
import org.example.wmsmvp.inboud.domain.LPN;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
                lpnBarcode,
                expirationAt
        );
        //when
        RestAssured.given().log().all()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/inbounds/inbound-items/{inboundItemNo}/lpns", inboundItemNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());


        //then
        final Inbound inbound = inboundRepository.findByInboundItemNo(inboundItemNo).get();
        final InboundItem inboundItem = inbound.testingGetInboundItemBy(inboundItemNo);
        final List<LPN> lpnList = inboundItem.getLpnList();
        assertThat(lpnList.size()).isEqualTo(1);
    }

}
