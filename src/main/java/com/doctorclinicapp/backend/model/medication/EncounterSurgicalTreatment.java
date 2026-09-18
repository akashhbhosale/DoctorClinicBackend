package com.doctorclinicapp.backend.model.medication;

import java.time.LocalDateTime;

import com.doctorclinicapp.backend.enums.Laterality;
import com.doctorclinicapp.backend.model.encounter.Encounter;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "encounter_surgical_treatment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncounterSurgicalTreatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "encounter_id", nullable = false)
    private Encounter encounter;

    @ManyToOne
    @JoinColumn(name = "surgery_master_id", nullable = false)
    private SurgeryMaster surgeryMaster;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Laterality laterality; // optional — some surgeries aren't left/right specific

    @Column(length = 255)
    private String site;

    @Column(length = 255)
    private String method;

    @Column(length = 255)
    private String device;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
