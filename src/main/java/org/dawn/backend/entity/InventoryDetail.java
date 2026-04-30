package org.dawn.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.dawn.backend.constant.inventory.DetailStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InventoryDetail {
    private Long id;
    private Long sessionId;
    private String imei;
    private Long expectedLoc;
    private Long actualLoc;
    private DetailStatus recordStatus;
    private String note;
}
