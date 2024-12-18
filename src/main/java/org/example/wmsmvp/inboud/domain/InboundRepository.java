package org.example.wmsmvp.inboud.domain;

import java.util.HashMap;
import java.util.Map;

public class InboundRepository {
    private final Map<Long, Inbound> inbounds = new HashMap<>();
    private Long sequence = 1L;


    public void save(final Inbound inbound) {
        inbound.assignId(sequence++);
        inbounds.put(inbound.getId(), inbound);
    }
}
