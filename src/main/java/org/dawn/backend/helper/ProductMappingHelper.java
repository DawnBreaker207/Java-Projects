package org.dawn.backend.helper;

import org.dawn.backend.dto.request.ProductRequest;
import org.dawn.backend.dto.response.ProductResponse;
import org.dawn.backend.entity.Product;

public interface ProductMappingHelper {
    static Product map(ProductRequest req) {
        return Product.builder()
                .sku(req.getSku())
                .name(req.getName())
                .priceImport(req.getPriceImport())
                .priceExport(req.getPriceExport())
                .currentStock(req.getCurrentStock())
                .minThreshold(req.getMinThreshold())
                .build();
    }

    static ProductResponse map(Product req) {
        return ProductResponse
                .builder()
                .id(req.getId())
                .sku(req.getSku())
                .name(req.getName())
                .priceImport(req.getPriceImport())
                .priceExport(req.getPriceExport())
                .currentStock(req.getCurrentStock())
                .minThreshold(req.getMinThreshold())
                .status(req.getStatus())
                .build();
    }
}
