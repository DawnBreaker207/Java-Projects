package org.dawn.backend.dto.catalog;

import org.dawn.backend.entity.Category;

public interface CategoryMappingHelper {
    static Category map(CategoryRequest req) {
        return Category.builder()
                .name(req.getName())
                .description(req.getDescription())
                .build();
    }

    static CategoryResponse map(Category req) {
        return CategoryResponse.builder()
                .id(req.getId())
                .name(req.getName())
                .description(req.getDescription())
                .items(req
                        .getItems()
                        .stream()
                        .map(ProductMappingHelper::map)
                        .toList())
                .createdAt(req.getCreatedAt())
                .updatedAt(req.getUpdatedAt())
                .build();
    }

}
