package org.example.wmsmvp.common;

import org.example.wmsmvp.inboud.feature.api.RegisterInboundApi;
import org.example.wmsmvp.product.feature.api.RegisterProductApi;

public class Scenario {
    public static RegisterProductApi registerProductApi() {
        return new RegisterProductApi();
    }

    public RegisterInboundApi registerInboundApi() {
        return new RegisterInboundApi();
    }
}
