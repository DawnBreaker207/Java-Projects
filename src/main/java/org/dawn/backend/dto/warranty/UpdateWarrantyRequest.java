package org.dawn.backend.dto.warranty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.dawn.backend.constant.warranty.WarrantyStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UpdateWarrantyRequest {
    private Long claimId;
    private WarrantyStatus status;
    private String technicalNote;
}
