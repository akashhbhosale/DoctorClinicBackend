package com.doctorclinicapp.backend.model.encounter;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "encounter_archive")
@Getter
@NoArgsConstructor
public class EncounterArchive {
    @Id @Column(name = "encounter_id")
    private Long encounterId;
    @Column(name = "archived_by", nullable = false)
    private String archivedBy;
    @Column(nullable = false, length = 500)
    private String reason;

    public EncounterArchive(Long encounterId, String archivedBy, String reason) {
        this.encounterId = encounterId;
        this.archivedBy = archivedBy;
        this.reason = reason;
    }
}
