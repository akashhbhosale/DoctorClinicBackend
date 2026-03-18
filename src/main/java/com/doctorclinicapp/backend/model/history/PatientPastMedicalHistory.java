package com.doctorclinicapp.backend.model.history;

import com.doctorclinicapp.backend.model.Patient;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "patient_past_medical_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientPastMedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "history_id", nullable = false)
    private PastMedicalHistoryMaster history;

    private Integer years;

    private Integer months;

    private Integer days;
}