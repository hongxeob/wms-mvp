package org.example.wmsmvp.inboud.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LPNRepository extends JpaRepository<LPN, Long> {
    Optional<LPN> findByLpnBarcode(String lpnBarcode);
}
