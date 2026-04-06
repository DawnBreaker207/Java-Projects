package org.dawn.backend.helper;

import org.dawn.backend.dto.response.UserResponse;
import org.dawn.backend.entity.User;

public interface UserMappingHelper {

    static UserResponse map(final User u) {
        return UserResponse
                .builder()
                .userId(u.getId())
                .username(u.getUsername())
                .role(u.getRole().getName().toString())
                .isDeleted(u.getIsDeleted())
                .createdAt(u.getCreatedAt())
                .updatedAt(u.getUpdatedAt())
                .build();
    }

}
