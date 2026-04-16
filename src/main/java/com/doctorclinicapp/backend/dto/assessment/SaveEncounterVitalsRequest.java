package com.doctorclinicapp.backend.dto.assessment;

import java.math.BigDecimal;

import com.doctorclinicapp.backend.model.assessment.TemperatureUnit;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class SaveEncounterVitalsRequest {

    @PositiveOrZero(message = "Systolic BP cannot be negative")
    private Integer systolicBp;

    @PositiveOrZero(message = "Diastolic BP cannot be negative")
    private Integer diastolicBp;

    @PositiveOrZero(message = "Pulse rate cannot be negative")
    private Integer pulseRate;

    @PositiveOrZero(message = "Respiratory rate cannot be negative")
    private Integer respiratoryRate;

    @Min(value = 0, message = "SpO2 cannot be less than 0")
    @Max(value = 100, message = "SpO2 cannot be more than 100")
    private Integer spo2;

    @PositiveOrZero(message = "Height cannot be negative")
    private BigDecimal height;

    @PositiveOrZero(message = "Weight cannot be negative")
    private BigDecimal weight;

    @PositiveOrZero(message = "Temperature cannot be negative")
    private BigDecimal temperature;

    private TemperatureUnit tempUnit;
}