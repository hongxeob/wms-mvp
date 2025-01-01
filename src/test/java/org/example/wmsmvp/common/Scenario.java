package org.example.wmsmvp.common;

import io.restassured.RestAssured;
import org.example.wmsmvp.inboud.feature.api.ConfirmInboundApi;
import org.example.wmsmvp.inboud.feature.api.RegisterInboundApi;
import org.example.wmsmvp.product.feature.api.RegisterProductApi;

public class Scenario {
    public static RegisterProductApi registerProduct() {
        return new RegisterProductApi();
    }

    public static RegisterInboundApi registerInbound() {
        return new RegisterInboundApi();
    }

    public static ConfirmInboundApi confirmInbound() {
        return new ConfirmInboundApi();
    }

    public static RejectInboundApi rejectInbound() {
        return new RejectInboundApi();
    }

    public static RegisterLPNApi registerLPN() {
        return new RegisterLPNApi();
    }
}
