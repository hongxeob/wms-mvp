package org.example.wmsmvp.inboud.feature.api;

import io.restassured.RestAssured;
import org.example.wmsmvp.common.Scenario;
import org.springframework.http.HttpStatus;

public class ConfirmInboundApi {
    private Long inboundNo = 1L;

    public Scenario request() {
        RestAssured.given().log().all()
                .when().post("/inbounds/{inboundNo}/confirm", inboundNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        return new Scenario();
    }
}
