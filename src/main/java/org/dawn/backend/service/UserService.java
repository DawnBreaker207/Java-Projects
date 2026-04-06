package org.dawn.backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dawn.backend.config.response.ResponsePage;
import org.dawn.backend.constant.Message;
import org.dawn.backend.constant.URole;
import org.dawn.backend.dto.response.UserResponse;
import org.dawn.backend.entity.Role;
import org.dawn.backend.entity.User;
import org.dawn.backend.exception.wrapper.ResourceNotFoundException;
import org.dawn.backend.helper.UserMappingHelper;
import org.dawn.backend.repository.RoleRepository;
import org.dawn.backend.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ResponsePage<UserResponse> findAll(Pageable pageable) {
        return ResponsePage.of(userRepository
                .findAll(pageable)
                .map(UserMappingHelper::map));
    }

    public UserResponse findOne(Long id) {
        return userRepository
                .findById(id)
                .map(UserMappingHelper::map)
                .orElseThrow(() -> new ResourceNotFoundException(Message.Exception.USER_NOT_FOUND));
    }

    public UserResponse findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .map(UserMappingHelper::map)
                .orElseThrow(() -> new ResourceNotFoundException(Message.Exception.USER_NOT_FOUND));
    }

    @Transactional
    public UserResponse updateStatus(Long id, Boolean status) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Message.Exception.USERNAME_NOT_FOUND));
        user.setIsDeleted(status);
        return UserMappingHelper.map(userRepository.save(user));
    }

    public boolean existsByRoleName(String roleName) {
        Role role = roleRepository
                .findByName(URole.valueOf(roleName))
                .orElseThrow(() -> new ResourceNotFoundException(Message.Exception.ROLE_NOT_FOUND));

        return userRepository.existsByRoleName(role.getName());
    }

    public Role findByRoleName(String roleName) {
        Role role = roleRepository
                .findByName(URole.valueOf(roleName))
                .orElseThrow(() -> new ResourceNotFoundException(Message.Exception.ROLE_NOT_FOUND));

        return Role.builder().name(role.getName()).build();
    }
}
