package com.doctorclinicapp.backend.model.nursing;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

/**
 * One logged intervention against a NursingAssessmentRecord. A nurse adds
 * several of these through an 8-9 hour shift against the same assessment,
 * each timestamped to when it was actually performed.
 */
@Entity
@Table(name = "nursing_intervention_entry")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NursingInterventionEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assessment_record_id", nullable = false)
    private NursingAssessmentRecord assessmentRecord;

    @ManyToOne
    @JoinColumn(name = "intervention_id", nullable = false)
    private NursingInterventionMaster intervention;

    // When the intervention was actually performed — nurse-entered,
    // defaults to "now" if not supplied (see service layer).
    @Column(name = "intervention_time", nullable = false)
    private LocalDateTime interventionTime;

    @Column(length = 500)
    private String note;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (interventionTime == null) {
            interventionTime = LocalDateTime.now();
        }
    }
}
