package com.doctorclinicapp.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import com.doctorclinicapp.backend.enums.BloodGroup;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Patient {

    // ABHA ID
    @Column(nullable = false, unique = true, length = 50)
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9-]{10,50}$", message = "ABHA ID must be alphanumeric with 10-50 characters")
    private String abhaId; // Field to store ABHA ID (unique)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(length = 80)
    @NotBlank @Size(max = 80)
    private String fullName;

    @Column(nullable = false, unique = true, length = 50)
    @Size(max = 50)
    private String registrationNo;
    
    @Column(nullable = false, length = 50)
    @NotBlank @Size(max = 50)
    private String gender; // Store gender as a String, "Male", "Female", etc.

    @Column(nullable = false)
    @NotNull
    private LocalDate dateOfBirth;

    @Column(nullable = false, unique = true, length = 100)
    @Email @NotBlank
    private String email;

    @Column(nullable = false, unique = true, length = 15)
    @NotBlank
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone must be 10–15 digits")
    private String phoneNo;

    // New fields
    @Column(nullable = false)
    @Min(0)
    private Integer age;  // Calculated or manually entered age of the patient

    @Column(length = 100)
    private String occupation;  // Occupation of the patient

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BloodGroup bloodGroup;  // Enum to store blood group

    @Column(length = 255)
    private String address;  // Patient's address

    @PrePersist
    private void prePersist() {
        if (this.registrationNo == null || this.registrationNo.isBlank()) {
            String token = java.util.UUID.randomUUID()
                    .toString().replace("-", "").substring(0, 8).toUpperCase();
            this.registrationNo = "PAT-" + token;  // Similar UUID pattern for Patient
        }

        // Optional: Calculate age based on the birthdate
        if (this.age == null && this.dateOfBirth != null) {
            this.age = LocalDate.now().getYear() - this.dateOfBirth.getYear();
        }
    }
}
