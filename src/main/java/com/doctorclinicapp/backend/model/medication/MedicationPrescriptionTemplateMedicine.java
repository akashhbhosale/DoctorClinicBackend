
package com.doctorclinicapp.backend.model.medication;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medication_prescription_template_medicines")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicationPrescriptionTemplateMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    private PrescriptionTemplate prescriptionTemplate;

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

    @Column(length = 100)
    private String strength;

    @Column(nullable = false, length = 100)
    private String dosage;

    @Column(nullable = false)
    private Integer durationDays;

    @Column(precision = 10, scale = 2)
    private BigDecimal quantity;

    @Column(length = 50)
    private String quantityUnit;

    @Column(length = 500)
    private String comment;
}
