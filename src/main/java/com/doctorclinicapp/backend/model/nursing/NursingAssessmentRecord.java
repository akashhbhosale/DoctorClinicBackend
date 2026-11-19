package com.doctorclinicapp.backend.model.nursing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.doctorclinicapp.backend.model.encounter.Encounter;

import jakarta.persistence.*;
import lombok.*;

/**
 * Replaces the old flat EncounterNursing row (which duplicated
 * Assessment+Diagnosis+Outcome for every intervention). Now: one
 * Assessment+Diagnosis+Outcome per record, with many NursingInterventionEntry
 * children logged underneath it as the nurse performs them through the
 * shift, each with its own timestamp.
 */
@Entity
@Table(name = "nursing_assessment_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NursingAssessmentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "encounter_id", nullable = false)
    private Encounter encounter;

    @ManyToOne
    @JoinColumn(name = "assessment_id", nullable = false)
    private NursingAssessmentMaster assessment;

    @ManyToOne
    @JoinColumn(name = "diagnosis_id", nullable = false)
    private NursingDiagnosisMaster diagnosis;

    @ManyToOne
    @JoinColumn(name = "outcome_id")
    private NursingOutcomeMaster outcome; // optional

    @Column(name = "outcome_score")
    private Integer outcomeScore;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "assessmentRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<NursingInterventionEntry> interventions = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
