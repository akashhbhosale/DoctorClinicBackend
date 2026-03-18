package com.doctorclinicapp.backend.model.history;

import com.doctorclinicapp.backend.model.Patient;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "patient_family_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientFamilyHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "history_id", nullable = false)
    private FamilyHistoryMaster history;

    @ManyToOne
    @JoinColumn(name = "relationship_id", nullable = false)
    private RelationshipMaster relationship;
}