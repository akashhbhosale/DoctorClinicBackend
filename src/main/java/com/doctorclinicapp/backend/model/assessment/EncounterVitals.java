package com.doctorclinicapp.backend.model.assessment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.doctorclinicapp.backend.model.encounter.Encounter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "encounter_vitals",
    uniqueConstraints = @UniqueConstraint(columnNames = "encounter_id")
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncounterVitals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "encounter_id", nullable = false, unique = true)
    private Encounter encounter;

    @Column(name = "systolic_bp")
    private Integer systolicBp;

    @Column(name = "diastolic_bp")
    private Integer diastolicBp;

    @Column(name = "pulse_rate")
    private Integer pulseRate;

    @Column(name = "respiratory_rate")
    private Integer respiratoryRate;

    @Column(name = "spo2")
    private Integer spo2;

    @Column(name = "height_cm", precision = 6, scale = 2)
    private BigDecimal height;

    @Column(name = "weight_kg", precision = 6, scale = 2)
    private BigDecimal weight;

    @Column(name = "temperature", precision = 5, scale = 2)
    private BigDecimal temperature;

    @Enumerated(EnumType.STRING)
    @Column(name = "temp_unit", length = 5)
    private TemperatureUnit tempUnit;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (tempUnit == null) {
            tempUnit = TemperatureUnit.C;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();

        if (tempUnit == null) {
            tempUnit = TemperatureUnit.C;
        }
    }
}