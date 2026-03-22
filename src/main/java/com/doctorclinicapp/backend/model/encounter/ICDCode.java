package com.doctorclinicapp.backend.model.encounter;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "icd_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ICDCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String description;
}