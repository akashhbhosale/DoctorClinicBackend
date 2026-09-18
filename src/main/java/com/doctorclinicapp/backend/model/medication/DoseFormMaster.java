package com.doctorclinicapp.backend.model.medication;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dose_form_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoseFormMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dose_form_name", nullable = false, unique = true, length = 255)
    private String doseFormName;
}
