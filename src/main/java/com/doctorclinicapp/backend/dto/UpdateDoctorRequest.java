package com.doctorclinicapp.backend.dto;

import com.doctorclinicapp.backend.enums.DoctorSpeciality;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateDoctorRequest {

    // optional: allow client to update username
    @NotBlank
    @Size(max = 50)
    private String username;

    // password can be updated too (send the new one; leave same if unchanged)
    @NotBlank
    @Size(min = 8, max = 64)
    private String password;

    @NotBlank
    @Size(max = 50)
    private String qualification;

    // keep as required; client must send a valid enum string
    private DoctorSpeciality speciality;

    @NotBlank
    @Size(max = 80)
    private String fullName;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone must be 10–15 digits")
    private String phoneNo;
}
