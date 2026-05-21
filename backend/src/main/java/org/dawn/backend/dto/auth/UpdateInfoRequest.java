package org.dawn.backend.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UpdateInfoRequest {

    private String fullName;

    private Integer gender;

    private Instant dob;

    private String phoneNumber;
}
