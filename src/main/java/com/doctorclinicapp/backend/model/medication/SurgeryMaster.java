package com.doctorclinicapp.backend.model.medication;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "surgery_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurgeryMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "surgery_name", nullable = false, unique = true, length = 255)
    private String surgeryName;
}
