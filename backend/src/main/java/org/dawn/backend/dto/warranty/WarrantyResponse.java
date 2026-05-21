package org.dawn.backend.dto.warranty;

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
public class WarrantyResponse {

    private Long id;

    private String imei;

    private String productName;

    private String customerName;

    private String customerPhone;

    private String issueDescription;

    private String staffName;

    private String staffUsername;

    private WarrantyStatus status;

    private Instant receivedDate;

    private Instant returnDate;
}
