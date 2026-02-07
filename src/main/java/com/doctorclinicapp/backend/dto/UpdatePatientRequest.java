package com.doctorclinicapp.backend.dto;

import com.doctorclinicapp.backend.enums.BloodGroup;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdatePatientRequest {

    @NotBlank
    @Size(max = 80)
    private String fullName;

    @NotBlank
    private String gender;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10,15}$")
    private String phoneNo;

    private BloodGroup bloodGroup;

    private String occupation;
    private String address;
}
