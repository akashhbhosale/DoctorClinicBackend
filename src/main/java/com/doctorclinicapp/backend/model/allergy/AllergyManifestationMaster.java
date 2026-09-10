package com.doctorclinicapp.backend.model.allergy;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "allergy_manifestation_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllergyManifestationMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "manifestation_name", nullable = false, unique = true)
    private String manifestationName;
}
