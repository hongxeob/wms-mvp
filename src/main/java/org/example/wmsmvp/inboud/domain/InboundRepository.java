package org.example.wmsmvp.inboud.domain;

import org.springframework.data.jpa.repository.JpaRepository;


public interface InboundRepository extends JpaRepository<Inbound, Long> {
    default Inbound getBy(Long inboundNo) {
        return findById(inboundNo).orElseThrow(
                () -> new IllegalArgumentException("해당 입고가 존재하지 않습니다."));
    }
}
