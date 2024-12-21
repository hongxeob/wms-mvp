package org.example.wmsmvp.inboud.feature;

import io.restassured.RestAssured;
import org.example.wmsmvp.common.ApiTest;
import org.example.wmsmvp.common.Scenario;
import org.example.wmsmvp.inboud.domain.InboundStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ConfirmInboundTest extends ApiTest {


    @Test
    @DisplayName("입고를 승인한다.")
    void confirmInbound() throws Exception {
        //given
        Scenario.registerProductApi().request()
                .registerInboundApi().request();
        Long inboundNo = 1L;

        //when
        RestAssured.given().log().all()
                .when().post("/inbounds/{inboundNo}/confirm")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
