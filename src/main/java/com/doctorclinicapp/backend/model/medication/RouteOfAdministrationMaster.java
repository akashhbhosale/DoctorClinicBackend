package com.doctorclinicapp.backend.model.medication;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "route_of_administration_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteOfAdministrationMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "route_name", nullable = false, unique = true, length = 255)
    private String routeName;
}
