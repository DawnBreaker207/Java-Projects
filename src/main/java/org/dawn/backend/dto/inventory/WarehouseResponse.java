package org.dawn.backend.dto.inventory;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.dawn.backend.dto.shared.BaseResponse;
import org.dawn.backend.entity.WarehouseLocation;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class WarehouseResponse extends BaseResponse {
    private String name;

    private String address;

    @Builder.Default
    private List<WarehouseLocationResponse> locations = new ArrayList<>();
}
