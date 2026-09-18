package com.doctorclinicapp.backend.model.medication;

import com.doctorclinicapp.backend.enums.MedicineType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicine_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medicine_name", nullable = false, length = 255)
    private String medicineName;

    // Drives the Generics/Brand radio toggle in the UI — the dropdown
    // is filtered to only show medicines of the selected type.
    @Enumerated(EnumType.STRING)
    @Column(name = "medicine_type", nullable = false, length = 20)
    private MedicineType medicineType;
}
