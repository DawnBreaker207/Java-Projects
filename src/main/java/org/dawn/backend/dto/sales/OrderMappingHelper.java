package org.dawn.backend.dto.sales;

import org.dawn.backend.entity.Order;

public interface OrderMappingHelper {
    static OrderResponse map(Order req) {
        return OrderResponse
                .builder()
                .id(req.getId())
                .customerId(req.getCustomerId())
                .customerName(req.getCustomer().getFullName())
                .customerPhone(req.getCustomer().getPhoneNumber())
                .totalAmount(req.getTotalAmount())
                .paymentMethod(req.getPaymentMethod())
                .status(req.getStatus())
                .createdAt(req.getCreatedAt())
                .build();
    }
}
