package com.doctorclinicapp.backend.model.assessment;

import java.time.LocalDateTime;

import com.doctorclinicapp.backend.model.encounter.Encounter;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "encounter_observation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncounterObservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "encounter_id", nullable = false)
    private Encounter encounter;

    @ManyToOne
    @JoinColumn(name = "observation_master_id", nullable = false)
    private ObservationMaster observationMaster;

    @Column(name = "assessment_result", columnDefinition = "TEXT")
    private String assessmentResult;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
