package com.doctorclinicapp.backend.dto.assessment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.doctorclinicapp.backend.model.assessment.TemperatureUnit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EncounterVitalsResponse {

    private Long id;
    private Long encounterId;

    private Integer systolicBp;
    private Integer diastolicBp;
    private Integer pulseRate;
    private Integer respiratoryRate;
    private Integer spo2;
    private BigDecimal height;
    private BigDecimal weight;
    private BigDecimal temperature;
    private TemperatureUnit tempUnit;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}