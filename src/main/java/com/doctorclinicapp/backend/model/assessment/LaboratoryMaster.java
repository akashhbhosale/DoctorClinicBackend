package com.doctorclinicapp.backend.model.assessment;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "laboratory_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaboratoryMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "test_name", nullable = false, unique = true, length = 255)
    private String testName;
}