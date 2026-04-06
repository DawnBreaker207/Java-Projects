package org.dawn.backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dawn.backend.constant.Message;
import org.dawn.backend.constant.URole;
import org.dawn.backend.dto.request.LoginRequest;
import org.dawn.backend.dto.request.RegisterRequest;
import org.dawn.backend.dto.response.JwtResponse;
import org.dawn.backend.dto.response.TokenRefreshResponse;
import org.dawn.backend.entity.RefreshToken;
import org.dawn.backend.entity.Role;
import org.dawn.backend.entity.User;
import org.dawn.backend.entity.UserDetailsImpl;
import org.dawn.backend.exception.wrapper.PermissionDeniedException;
import org.dawn.backend.exception.wrapper.ResourceAlreadyExistedException;
import org.dawn.backend.exception.wrapper.ResourceNotFoundException;
import org.dawn.backend.repository.RoleRepository;
import org.dawn.backend.repository.UserRepository;
import org.dawn.backend.utils.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;

    public void register(RegisterRequest newUser) {
        userRepository.findByUsername(newUser.getUsername()).ifPresent(u -> {
            throw new ResourceAlreadyExistedException(Message.Exception.USERNAME_EXISTED);
        });

        User user = User
                .builder()
                .username(newUser.getUsername())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .build();

        Role userRole = roleRepository
                .findByName(URole.valueOf(newUser.getRole()))
                .orElseThrow(() -> new ResourceNotFoundException(Message.Exception.ROLE_NOT_FOUND));

        user.setRole(userRole);
        userRepository.save(user);
        log.info("User registered successfully: {}", newUser.getUsername());
    }

    @Transactional
    public JwtResponse login(LoginRequest user) {

        String identifier = user.getUsername();
        log.info("Get username :{}", identifier);
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(identifier,
                                user.getPassword()));
        log.info("Get authenticated {}", authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        log.info("Get user detail: {}", userDetails);
        List<String> role = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        log.info("Get role {}", role);
        String jwt = jwtUtils.generateToken(userDetails.getUsername(), role);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return JwtResponse
                .builder()
                .userId(userDetails.getId())
                .username(userDetails.getUsername())
                .accessToken(jwt)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public void changePassword(String username, String oldPassword, String newPassword) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(Message.Exception.USERNAME_NOT_FOUND));
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new PermissionDeniedException(Message.Exception.PASSWORD_NOT_MATCH);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


    @Transactional
    public TokenRefreshResponse refreshToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new ResourceNotFoundException(Message.Exception.REFRESH_TOKEN_EXPIRED);
        }

        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String jwtCookie = jwtUtils.generateToken(user.getUsername());
                    return TokenRefreshResponse
                            .builder()
                            .accessToken(jwtCookie)
                            .build();
                })
                .orElseThrow(() -> new ResourceNotFoundException(Message.Exception.REFRESH_TOKEN_NOT_FOUND));
    }
}
