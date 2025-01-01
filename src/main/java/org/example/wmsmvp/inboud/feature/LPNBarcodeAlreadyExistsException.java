package org.example.wmsmvp.inboud.feature;

public class LPNBarcodeAlreadyExistsException extends RuntimeException {
    public LPNBarcodeAlreadyExistsException(final String lpnBarcode) {
        super(lpnBarcode + "는 이미 등록된 LPN입니다.");
    }
}
