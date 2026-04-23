package org.dawn.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.dawn.backend.constant.WarrantyStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UpdateWarrantyRequest {
    Long claimId;
    WarrantyStatus status;
    String technicalNote;
}
