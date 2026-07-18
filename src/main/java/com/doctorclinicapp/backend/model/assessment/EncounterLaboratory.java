package com.doctorclinicapp.backend.model.assessment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.doctorclinicapp.backend.model.encounter.Encounter;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "encounter_laboratory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncounterLaboratory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "encounter_id", nullable = false)
    private Encounter encounter;

    @ManyToOne
    @JoinColumn(name = "laboratory_master_id", nullable = false)
    private LaboratoryMaster laboratoryMaster;

    @Column(name = "lab_result", columnDefinition = "TEXT")
    private String labResult;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "laboratory", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<EncounterLaboratoryFile> files = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}