package com.doctorclinicapp.backend.model.encounter;

import jakarta.persistence.*;
import lombok.*;

import com.doctorclinicapp.backend.enums.DiagnosisType;

@Entity
@Table(name = "encounter_diagnosis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"encounter", "icdCode"})
public class EncounterDiagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encounter_id", nullable = false)
    private Encounter encounter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icd_code_id", nullable = false)
    private ICDCode icdCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "diagnosis_type", nullable = false)
    private DiagnosisType diagnosisType;
}