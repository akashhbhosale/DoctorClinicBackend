package com.doctorclinicapp.backend.model.encounter;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "encounter_chief_complaints")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncounterChiefComplaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "encounter_id", nullable = false)
    private Encounter encounter;

    @ManyToOne
    @JoinColumn(name = "complaint_id", nullable = false)
    private ChiefComplaint chiefComplaint;

    @Column(name = "time_since_days")
    private Integer timeSinceDays;
}