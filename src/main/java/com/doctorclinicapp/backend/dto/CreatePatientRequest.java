package com.doctorclinicapp.backend.dto;

import com.doctorclinicapp.backend.enums.BloodGroup;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreatePatientRequest {

    @NotBlank
    private String abhaId;

    @NotBlank
    @Size(max = 80)
    private String fullName;

    @NotBlank
    private String gender;

    @NotNull
    private LocalDate dateOfBirth;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10,15}$")
    private String phoneNo;

    @Min(0)
    private Integer age;

    private String occupation;

    @NotNull
    private BloodGroup bloodGroup;

    private String address;
}
