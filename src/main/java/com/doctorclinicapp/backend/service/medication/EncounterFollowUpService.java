package com.doctorclinicapp.backend.service.medication;

import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.medication.EncounterFollowUpRequest;
import com.doctorclinicapp.backend.dto.medication.EncounterFollowUpResponse;
import com.doctorclinicapp.backend.exception.ResourceNotFoundException;
import com.doctorclinicapp.backend.model.encounter.Encounter;
import com.doctorclinicapp.backend.model.medication.EncounterFollowUp;
import com.doctorclinicapp.backend.repository.encounter.EncounterRepository;
import com.doctorclinicapp.backend.repository.medication.EncounterFollowUpRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncounterFollowUpService {

    private final EncounterFollowUpRepository repository;
    private final EncounterRepository encounterRepo;

    // Upsert — same idea as EncounterVitalsService: one record per
    // encounter, save creates it if missing or updates it if present.
    public EncounterFollowUpResponse saveFollowUp(Long encounterId, EncounterFollowUpRequest req) {
        Encounter encounter = encounterRepo.findById(encounterId)
                .orElseThrow(() -> new ResourceNotFoundException("Encounter", "id", encounterId));

        EncounterFollowUp entity = repository.findByEncounterId(encounterId)
                .orElseGet(() -> {
                    EncounterFollowUp newEntity = new EncounterFollowUp();
                    newEntity.setEncounter(encounter);
                    return newEntity;
                });

        entity.setFollowUpDate(req.getFollowUpDate());
        entity.setFollowUpInstructions(req.getFollowUpInstructions());

        EncounterFollowUp saved = repository.save(entity);
        return mapToResponse(saved);
    }

    public EncounterFollowUpResponse getByEncounter(Long encounterId) {
        return repository.findByEncounterId(encounterId)
                .map(this::mapToResponse)
                .orElse(null);
    }

    private EncounterFollowUpResponse mapToResponse(EncounterFollowUp e) {
        return EncounterFollowUpResponse.builder()
                .id(e.getId())
                .encounterId(e.getEncounter().getId())
                .followUpDate(e.getFollowUpDate())
                .followUpInstructions(e.getFollowUpInstructions())
                .build();
    }
}
