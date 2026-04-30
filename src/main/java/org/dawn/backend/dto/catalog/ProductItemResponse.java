package org.dawn.backend.dto.catalog;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.dawn.backend.constant.catalog.ItemStatus;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductItemResponse {
    private Long id;
    private String imei;
    private ItemStatus status;
    private Instant importDate;
    private Instant soldDate;
}
