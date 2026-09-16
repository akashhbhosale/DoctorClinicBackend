package com.doctorclinicapp.backend.service.assessment.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.assessment.AddEncounterObservationRequest;
import com.doctorclinicapp.backend.dto.assessment.EncounterObservationResponse;
import com.doctorclinicapp.backend.exception.ResourceNotFoundException;
import com.doctorclinicapp.backend.model.assessment.EncounterObservation;
import com.doctorclinicapp.backend.model.assessment.ObservationMaster;
import com.doctorclinicapp.backend.model.encounter.Encounter;
import com.doctorclinicapp.backend.repository.assessment.EncounterObservationRepository;
import com.doctorclinicapp.backend.repository.assessment.ObservationMasterRepository;
import com.doctorclinicapp.backend.repository.encounter.EncounterRepository;
import com.doctorclinicapp.backend.service.assessment.EncounterObservationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncounterObservationServiceImpl implements EncounterObservationService {

    private final EncounterRepository encounterRepository;
    private final EncounterObservationRepository encounterObservationRepository;
    private final ObservationMasterRepository observationMasterRepository;

    @Override
    public EncounterObservationResponse addObservation(AddEncounterObservationRequest request) {
        Encounter encounter = encounterRepository.findById(request.getEncounterId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Encounter not found with id: " + request.getEncounterId()));

        ObservationMaster observationMaster = observationMasterRepository.findById(request.getObservationMasterId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Observation master not found with id: " + request.getObservationMasterId()));

        EncounterObservation observation = EncounterObservation.builder()
                .encounter(encounter)
                .observationMaster(observationMaster)
                .assessmentResult(request.getAssessmentResult())
                .build();

        EncounterObservation saved = encounterObservationRepository.save(observation);
        return mapToResponse(saved);
    }

    @Override
    public List<EncounterObservationResponse> getObservationsByEncounterId(Long encounterId) {
        encounterRepository.findById(encounterId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Encounter not found with id: " + encounterId));

        return encounterObservationRepository.findByEncounterIdOrderByCreatedAtDesc(encounterId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteObservation(Long observationId) {
        EncounterObservation observation = encounterObservationRepository.findById(observationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Observation record not found with id: " + observationId));

        encounterObservationRepository.delete(observation);
    }

    private EncounterObservationResponse mapToResponse(EncounterObservation observation) {
        return EncounterObservationResponse.builder()
                .id(observation.getId())
                .encounterId(observation.getEncounter().getId())
                .observationMasterId(observation.getObservationMaster().getId())
                .observationName(observation.getObservationMaster().getObservationName())
                .assessmentResult(observation.getAssessmentResult())
                .createdAt(observation.getCreatedAt())
                .updatedAt(observation.getUpdatedAt())
                .build();
    }
}
