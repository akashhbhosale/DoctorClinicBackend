package com.doctorclinicapp.backend.model.assessment;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "observation_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObservationMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "observation_name", nullable = false, unique = true, length = 255)
    private String observationName;
}
