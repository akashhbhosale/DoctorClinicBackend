package com.doctorclinicapp.backend.service.assessment.impl;

import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.assessment.EncounterVitalsResponse;
import com.doctorclinicapp.backend.dto.assessment.SaveEncounterVitalsRequest;
import com.doctorclinicapp.backend.exception.ResourceNotFoundException;
import com.doctorclinicapp.backend.model.assessment.EncounterVitals;
import com.doctorclinicapp.backend.model.assessment.TemperatureUnit;
import com.doctorclinicapp.backend.model.encounter.Encounter;
import com.doctorclinicapp.backend.repository.assessment.EncounterVitalsRepository;
import com.doctorclinicapp.backend.repository.encounter.EncounterRepository;
import com.doctorclinicapp.backend.service.assessment.EncounterVitalsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncounterVitalsServiceImpl implements EncounterVitalsService {

    private final EncounterRepository encounterRepository;
    private final EncounterVitalsRepository encounterVitalsRepository;

    @Override
    public EncounterVitalsResponse saveOrUpdateVitals(Long encounterId, SaveEncounterVitalsRequest request) {

        Encounter encounter = encounterRepository.findById(encounterId)
                .orElseThrow(() -> new ResourceNotFoundException("Encounter not found with id: " + encounterId));

        EncounterVitals vitals = encounterVitalsRepository.findByEncounterId(encounterId)
                .orElse(EncounterVitals.builder()
                        .encounter(encounter)
                        .build());

        vitals.setSystolicBp(request.getSystolicBp());
        vitals.setDiastolicBp(request.getDiastolicBp());
        vitals.setPulseRate(request.getPulseRate());
        vitals.setRespiratoryRate(request.getRespiratoryRate());
        vitals.setSpo2(request.getSpo2());
        vitals.setHeight(request.getHeight());
        vitals.setWeight(request.getWeight());
        vitals.setTemperature(request.getTemperature());
        vitals.setTempUnit(request.getTempUnit() != null ? request.getTempUnit() : TemperatureUnit.C);

        EncounterVitals savedVitals = encounterVitalsRepository.save(vitals);

        return mapToResponse(savedVitals);
    }

    @Override
    public EncounterVitalsResponse getVitalsByEncounterId(Long encounterId) {
        EncounterVitals vitals = encounterVitalsRepository.findByEncounterId(encounterId)
                .orElseThrow(() -> new ResourceNotFoundException("Vitals not found for encounter id: " + encounterId));
        return mapToResponse(vitals);
    }

    @Override
    public void deleteVitalsByEncounterId(Long encounterId) {
        EncounterVitals vitals = encounterVitalsRepository.findByEncounterId(encounterId)
                .orElseThrow(() -> new ResourceNotFoundException("Vitals not found for encounter id: " + encounterId));
        encounterVitalsRepository.delete(vitals);
    }

    private EncounterVitalsResponse mapToResponse(EncounterVitals vitals) {
        return EncounterVitalsResponse.builder()
                .id(vitals.getId())
                .encounterId(vitals.getEncounter().getId())
                .systolicBp(vitals.getSystolicBp())
                .diastolicBp(vitals.getDiastolicBp())
                .pulseRate(vitals.getPulseRate())
                .respiratoryRate(vitals.getRespiratoryRate())
                .spo2(vitals.getSpo2())
                .height(vitals.getHeight())
                .weight(vitals.getWeight())
                .temperature(vitals.getTemperature())
                .tempUnit(vitals.getTempUnit())
                .createdAt(vitals.getCreatedAt())
                .updatedAt(vitals.getUpdatedAt())
                .build();
    }
}