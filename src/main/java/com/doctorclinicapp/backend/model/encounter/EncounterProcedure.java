package com.doctorclinicapp.backend.model.encounter;

import jakarta.persistence.*;
import lombok.*;

import com.doctorclinicapp.backend.enums.Laterality;
import com.doctorclinicapp.backend.enums.Priority;
import com.doctorclinicapp.backend.model.procedure.*;

@Entity
@Table(name = "encounter_procedure")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncounterProcedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Encounter
    @ManyToOne
    @JoinColumn(name = "encounter_id", nullable = false)
    private Encounter encounter;

    // 🔗 Procedure Master
    @ManyToOne
    @JoinColumn(name = "procedure_id", nullable = false)
    private ProcedureMaster procedure;

    // 🔗 Site (optional)
    @ManyToOne
    @JoinColumn(name = "site_id")
    private ProcedureSiteMaster site;

    // 🔗 Device (optional)
    @ManyToOne
    @JoinColumn(name = "device_id")
    private ProcedureDeviceMaster device;

    // 🔗 Method (optional)
    @ManyToOne
    @JoinColumn(name = "method_id")
    private ProcedureMethodMaster method;

    // 🔥 ENUMS
    @Enumerated(EnumType.STRING)
    private Laterality laterality;

    @Enumerated(EnumType.STRING)
    private Priority priority;
}