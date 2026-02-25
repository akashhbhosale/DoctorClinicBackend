package com.doctorclinicapp.backend.dto;

import com.doctorclinicapp.backend.enums.BloodGroup;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UpdatePatientRequest {
	@NotBlank
	@Pattern(
	   regexp = "^[A-Za-z0-9-]{10,50}$",
	   message = "ABHA ID must be alphanumeric with 10-50 characters"
	)
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

    private BloodGroup bloodGroup;

    private String occupation;
    private String address;
}
