package com.doctorclinicapp.backend.model.allergy;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "allergy_substance_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllergySubstanceMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "substance_name", nullable = false, unique = true)
    private String substanceName;
}
