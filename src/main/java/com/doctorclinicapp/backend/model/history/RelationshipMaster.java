package com.doctorclinicapp.backend.model.history;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "relationship_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelationshipMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "relationship_name", nullable = false)
    private String relationshipName;
}