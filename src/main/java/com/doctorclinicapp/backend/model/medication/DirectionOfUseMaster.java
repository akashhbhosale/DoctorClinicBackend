package com.doctorclinicapp.backend.model.medication;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "direction_of_use_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectionOfUseMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "direction_name", nullable = false, unique = true, length = 255)
    private String directionName;
}
