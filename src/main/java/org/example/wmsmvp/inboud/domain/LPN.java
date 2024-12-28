package org.example.wmsmvp.inboud.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "lpn")
@Comment("LPN")
@Getter
public class LPN {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lpnNo;

    @Comment("LPN 바코드(중복 불가")
    @Column(name = "lpn_barcode", nullable = false, unique = true)
    private String lpnBarcode;

    @Comment("유통기한")
    @Column(name = "expiration_at", nullable = false)
    private LocalDateTime expirationAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inbound_item_id", nullable = false)
    @Comment("입고 상품 ID")
    private InboundItem inboundItem;

    public LPN(final String lpnBarcode, final LocalDateTime expirationAt, final InboundItem inboundItem) {
        validateConstructor(lpnBarcode, expirationAt, inboundItem);
        this.lpnBarcode = lpnBarcode;
        this.expirationAt = expirationAt;
        this.inboundItem = inboundItem;
    }

    private void validateConstructor(final String lpnBarcode, final LocalDateTime expirationAt, final InboundItem inboundItem) {
        Assert.hasText(lpnBarcode, "LPN 바코드는 필수입니다.");
        Assert.notNull(expirationAt, "유통기한은 필수입니다.");
        Assert.notNull(inboundItem, "입고 상품은 필수입니다.");
    }
}
