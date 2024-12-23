package org.example.wmsmvp.common;

import org.example.wmsmvp.inboud.feature.ConfirmInboundApi;
import org.example.wmsmvp.inboud.feature.api.RegisterInboundApi;
import org.example.wmsmvp.product.feature.api.RegisterProductApi;

public class Scenario {
    public static RegisterProductApi registerProduct() {
        return new RegisterProductApi();
    }

    public RegisterInboundApi registerInbound() {
        return new RegisterInboundApi();
    }

    public ConfirmInboundApi confirmInbound() {
        return new ConfirmInboundApi();
    }

    public RejectInboundApi rejectInbound() {
        return new RejectInboundApi();
    }
}
