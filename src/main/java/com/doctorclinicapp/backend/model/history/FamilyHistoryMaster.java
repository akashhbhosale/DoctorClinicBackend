package com.doctorclinicapp.backend.model.history;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "family_history_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyHistoryMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "history_name", nullable = false)
    private String historyName;
}