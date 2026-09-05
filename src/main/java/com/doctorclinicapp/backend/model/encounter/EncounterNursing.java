package com.doctorclinicapp.backend.model.encounter;

import com.doctorclinicapp.backend.model.nursing.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "encounter_nursing")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncounterNursing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Encounter
    @ManyToOne
    @JoinColumn(name = "encounter_id", nullable = false)
    private Encounter encounter;

    // 🔗 Assessment (required)
    @ManyToOne
    @JoinColumn(name = "assessment_id", nullable = false)
    private NursingAssessmentMaster assessment;

    // 🔗 Diagnosis (required)
    @ManyToOne
    @JoinColumn(name = "diagnosis_id", nullable = false)
    private NursingDiagnosisMaster diagnosis;

    // 🔗 Outcome (optional)
    @ManyToOne
    @JoinColumn(name = "outcome_id")
    private NursingOutcomeMaster outcome;

    // Outcome score (optional, paired with outcome)
    @Column(name = "outcome_score")
    private Integer outcomeScore;

    // 🔗 Intervention (required) — this is the one that repeats per row,
    // matching "multiple interventions for a single encounter"
    @ManyToOne
    @JoinColumn(name = "intervention_id", nullable = false)
    private NursingInterventionMaster intervention;
}
