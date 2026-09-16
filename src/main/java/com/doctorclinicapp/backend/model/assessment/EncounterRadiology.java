package com.doctorclinicapp.backend.model.assessment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.doctorclinicapp.backend.model.encounter.Encounter;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "encounter_radiology")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncounterRadiology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "encounter_id", nullable = false)
    private Encounter encounter;

    @ManyToOne
    @JoinColumn(name = "radiology_master_id", nullable = false)
    private RadiologyMaster radiologyMaster;

    @Column(name = "radiology_result", columnDefinition = "TEXT")
    private String radiologyResult;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "radiology", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<EncounterRadiologyFile> files = new ArrayList<>();

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
