package com.doctorclinicapp.backend.model;

import com.doctorclinicapp.backend.enums.DoctorSpeciality;
import com.fasterxml.jackson.annotation.JsonProperty; // <-- add this import
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "password")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
 // Add this field (place near other basic fields)
    @Column(length = 80)                 // keep DB nullable to avoid migration issues
    @NotBlank @Size(max = 80)           // enforce on new requests
    private String fullName;

    @Column(nullable = false, unique = true, length = 50, updatable = false)
    @Size(max = 50)                                   // <-- removed @NotBlank
    private String registrationNo;

    @Column(nullable = false, unique = true, length = 50)
    @NotBlank @Size(max = 50)
    private String username;

    @Column(nullable = false)
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)   // <-- was @JsonIgnore
    private String password;

    @Column(nullable = false, length = 50)
    @NotBlank @Size(max = 50)
    private String qualification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private DoctorSpeciality speciality;

    @Column(nullable = false, unique = true, length = 100)
    @Email @NotBlank
    private String email;

    @Column(nullable = false, unique = true, length = 15)
    @NotBlank
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone must be 10–15 digits")
    private String phoneNo;

    @PrePersist
    private void prePersist() {
        if (this.registrationNo == null || this.registrationNo.isBlank()) {
            String token = java.util.UUID.randomUUID()
                    .toString().replace("-", "").substring(0, 8).toUpperCase();
            this.registrationNo = "DOC-" + token;
        }
    }
}
