package org.dawn.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.dawn.backend.constant.inventory.SessionStatus;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InventorySession {
    private Long id;
    private Long createdBy;
    private SessionStatus status;
    private Instant startDate;
    private Instant endDate;
}
