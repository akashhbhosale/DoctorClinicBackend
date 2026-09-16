package com.doctorclinicapp.backend.model.assessment;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "radiology_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RadiologyMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_name", nullable = false, unique = true, length = 255)
    private String orderName;
}
