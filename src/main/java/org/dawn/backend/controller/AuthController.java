package org.dawn.backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dawn.backend.config.response.ResponseObject;
import org.dawn.backend.dto.request.LoginRequest;
import org.dawn.backend.dto.request.RegisterRequest;
import org.dawn.backend.dto.response.JwtResponse;
import org.dawn.backend.dto.response.TokenRefreshResponse;
import org.dawn.backend.service.AuthService;
import org.dawn.backend.utils.JWTUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Operations related to auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final JWTUtils jwtUtils;

    @PostMapping("/register")
    public ResponseObject<String> register(@RequestBody RegisterRequest newUser) {
        authService.register(newUser);
        return ResponseObject.success("");
    }

    @PostMapping("/login")
    public ResponseObject<JwtResponse> login(@RequestBody LoginRequest user) {
        JwtResponse jwt = authService.login(user);
        return new ResponseObject<>(
                HttpStatus.OK,
                "Success",
                jwt,
                new HttpHeaders() {
                    {
                        add(
                                HttpHeaders.SET_COOKIE,
                                jwtUtils.generateJwtRefreshCookie(jwt.getRefreshToken())
                                        .toString());
                    }
                });
    }

    @PostMapping("/refresh-token")
    public ResponseObject<TokenRefreshResponse> refreshToken(@CookieValue("${app.jwtRefreshCookieName}") String refreshToken) {
        TokenRefreshResponse token = authService.refreshToken(refreshToken);
        return new ResponseObject<>(
                HttpStatus.OK,
                "Success",
                token,
                new HttpHeaders() {{
                    add(
                            HttpHeaders.SET_COOKIE,
                            jwtUtils.generateJwtRefreshCookie(token.getRefreshToken())
                                    .toString());
                }});

    }
}
