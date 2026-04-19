package org.dawn.backend.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.dawn.backend.constant.ItemStatus;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(exclude = "product")
public class ProductItem {

    private Long id;

    private Long productId;

    private String imei;

    private ItemStatus status = ItemStatus.AVAILABLE;

    private Long orderId;

    @Builder.Default
    private Instant importDate = Instant.now();

    private Instant soldDate;

    private Product product;
}
