package com.doctorclinicapp.backend.model.medication;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.doctorclinicapp.backend.model.encounter.Encounter;

import jakarta.persistence.*;
import lombok.*;

/**
 * One per encounter (unique on encounter_id) — same upsert pattern as
 * EncounterVitals, not a repeatable "+" list like Medication/Surgical
 * Treatment. The screenshot shows a single pair of fields with no
 * add/table UI, just saved once per visit.
 */
@Entity
@Table(name = "encounter_follow_up")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncounterFollowUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "encounter_id", nullable = false, unique = true)
    private Encounter encounter;

    @Column(name = "follow_up_date")
    private LocalDate followUpDate;

    @Column(name = "follow_up_instructions", length = 1000)
    private String followUpInstructions;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void touch() {
        updatedAt = LocalDateTime.now();
    }
}
