package org.example.wmsmvp.inboud.domain;

import org.springframework.data.jpa.repository.JpaRepository;


public interface InboundRepository extends JpaRepository<Inbound, Long> {
    default Inbound getBy(Long inboundNo) {
        return findById(inboundNo).orElseThrow(
                () -> new IllegalArgumentException("inbound not found"));
    }
}
