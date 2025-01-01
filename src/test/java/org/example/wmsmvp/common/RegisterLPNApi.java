package org.example.wmsmvp.common;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.wmsmvp.inboud.feature.RegisterLPN;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class RegisterLPNApi extends RestAssured {
    private final Long inboundItemNo = 1L;
    private final String lpnBarcode = "LPN-1234";
    private final LocalDateTime expirationAt = LocalDateTime.now().plusDays(1);

    public RegisterLPNApi inboundItemNo(Long inboundItemNo) {
        return this;
    }

    public RegisterLPNApi lpnBarcode(String lpnBarcode) {
        return this;
    }

    public RegisterLPNApi expirationAt(LocalDateTime expirationAt) {
        return this;
    }

    public void request() {
        RegisterLPN.Request request = new RegisterLPN.Request(
                lpnBarcode,
                expirationAt
        );
        RestAssured.given().log().all()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/inbounds/inbound-items/{inboundItemNo}/lpns", inboundItemNo)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}
