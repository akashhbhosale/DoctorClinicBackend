package com.doctorclinicapp.backend.model.medication;

import java.time.LocalDateTime;

import com.doctorclinicapp.backend.model.encounter.Encounter;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "encounter_medication")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncounterMedication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "encounter_id", nullable = false)
    private Encounter encounter;

    @ManyToOne
    @JoinColumn(name = "medicine_master_id", nullable = false)
    private MedicineMaster medicineMaster;

    @ManyToOne
    @JoinColumn(name = "dose_form_id")
    private DoseFormMaster doseForm;

    @ManyToOne
    @JoinColumn(name = "route_of_administration_id")
    private RouteOfAdministrationMaster routeOfAdministration;

    @ManyToOne
    @JoinColumn(name = "direction_of_use_id", nullable = false)
    private DirectionOfUseMaster directionOfUse;

    @Column(nullable = false, length = 100)
    private String dosage;

    @Column(nullable = false)
    private Integer durationDays;

    @Column(length = 500)
    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
