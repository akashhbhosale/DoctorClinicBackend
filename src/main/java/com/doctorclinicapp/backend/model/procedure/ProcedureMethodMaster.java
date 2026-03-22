package com.doctorclinicapp.backend.model.procedure;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "procedure_method_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcedureMethodMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}