package com.doctorclinicapp.backend.model.encounter;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chief_complaints")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiefComplaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="complaint_name", nullable=false)
    private String complaintName;

}