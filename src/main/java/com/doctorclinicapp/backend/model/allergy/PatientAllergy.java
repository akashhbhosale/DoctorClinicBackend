package com.doctorclinicapp.backend.model.allergy;

import java.util.List;

import com.doctorclinicapp.backend.enums.AllergyCriticality;
import com.doctorclinicapp.backend.enums.AllergyVerificationStatus;
import com.doctorclinicapp.backend.model.Patient;

import jakarta.persistence.*;
import lombok.*;

/**
 * Tied directly to Patient (not to a single Encounter) — same pattern as
 * PatientPastMedicalHistory / PatientFamilyHistory, since an allergy is
 * usually a lifelong fact about the patient, not something scoped to one
 * visit.
 */
@Entity
@Table(name = "patient_allergy")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientAllergy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "substance_id", nullable = false)
    private AllergySubstanceMaster substance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AllergyCriticality criticality;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false, length = 20)
    private AllergyVerificationStatus verificationStatus;

    @Column(length = 500)
    private String comment;

    // A single allergy can present with multiple manifestations
    // (e.g. both "Itchy" and "Breathless" for the same substance).
    @ManyToMany
    @JoinTable(
        name = "patient_allergy_manifestation",
        joinColumns = @JoinColumn(name = "allergy_id"),
        inverseJoinColumns = @JoinColumn(name = "manifestation_id")
    )
    private List<AllergyManifestationMaster> manifestations;
}
