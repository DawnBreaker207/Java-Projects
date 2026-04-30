package org.dawn.backend.dto.warranty;

import org.dawn.backend.entity.Warranty;

public interface WarrantyMappingHelper {
    static WarrantyResponse map(Warranty req) {
        return WarrantyResponse.builder()
                .id(req.getId())
                .imei(req.getProductItem().getImei())
                .productName(req.getProductItem().getProduct().getName())
                .customerName(req.getCustomer().getFullName())
                .customerPhone(req.getCustomer().getPhoneNumber())
                .issueDescription(req.getIssueDescription())
                .status(req.getStatus())
                .staffName(req.getStaff().getFullName())
                .staffUsername(req.getStaff().getUsername())
                .receivedDate(req.getReceivedDate())
                .returnDate(req.getReturnDate())
                .build();
    }
}
