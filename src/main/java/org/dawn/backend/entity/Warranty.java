package org.dawn.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.dawn.backend.constant.warranty.WarrantyStatus;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Warranty {
    private Long id;

    private Long productItemId;

    private Long customerId;

    private Long createdBy;

    private String issueDescription;

    private WarrantyStatus status;

    private Instant receivedDate;

    private Instant returnDate;
}
