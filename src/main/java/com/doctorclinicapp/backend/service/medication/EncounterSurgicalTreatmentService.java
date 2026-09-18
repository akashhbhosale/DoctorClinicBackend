package com.doctorclinicapp.backend.service.medication;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.medication.AddEncounterSurgicalTreatmentRequest;
import com.doctorclinicapp.backend.dto.medication.EncounterSurgicalTreatmentResponse;
import com.doctorclinicapp.backend.enums.Laterality;
import com.doctorclinicapp.backend.exception.ResourceNotFoundException;
import com.doctorclinicapp.backend.model.encounter.Encounter;
import com.doctorclinicapp.backend.model.medication.EncounterSurgicalTreatment;
import com.doctorclinicapp.backend.model.medication.SurgeryMaster;
import com.doctorclinicapp.backend.repository.encounter.EncounterRepository;
import com.doctorclinicapp.backend.repository.medication.EncounterSurgicalTreatmentRepository;
import com.doctorclinicapp.backend.repository.medication.SurgeryMasterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncounterSurgicalTreatmentService {

    private final EncounterSurgicalTreatmentRepository repository;
    private final EncounterRepository encounterRepo;
    private final SurgeryMasterRepository surgeryRepo;

    public EncounterSurgicalTreatmentResponse addSurgicalTreatment(AddEncounterSurgicalTreatmentRequest req) {
        Encounter encounter = encounterRepo.findById(req.getEncounterId())
                .orElseThrow(() -> new ResourceNotFoundException("Encounter", "id", req.getEncounterId()));

        SurgeryMaster surgery = surgeryRepo.findById(req.getSurgeryMasterId())
                .orElseThrow(() -> new ResourceNotFoundException("Surgery", "id", req.getSurgeryMasterId()));

        EncounterSurgicalTreatment entity = new EncounterSurgicalTreatment();
        entity.setEncounter(encounter);
        entity.setSurgeryMaster(surgery);
        entity.setSite(req.getSite());
        entity.setMethod(req.getMethod());
        entity.setDevice(req.getDevice());

        if (req.getLaterality() != null && !req.getLaterality().isBlank()) {
            try {
                entity.setLaterality(Laterality.valueOf(req.getLaterality().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException("Invalid laterality value");
            }
        }

        EncounterSurgicalTreatment saved = repository.save(entity);
        return mapToResponse(saved);
    }

    public List<EncounterSurgicalTreatmentResponse> getByEncounter(Long encounterId) {
        return repository.findByEncounterIdOrderByCreatedAtDesc(encounterId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteSurgicalTreatment(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("EncounterSurgicalTreatment", "id", id);
        }
        repository.deleteById(id);
    }

    private EncounterSurgicalTreatmentResponse mapToResponse(EncounterSurgicalTreatment e) {
        return EncounterSurgicalTreatmentResponse.builder()
                .id(e.getId())
                .encounterId(e.getEncounter().getId())
                .surgeryMasterId(e.getSurgeryMaster().getId())
                .surgeryName(e.getSurgeryMaster().getSurgeryName())
                .laterality(e.getLaterality() != null ? e.getLaterality().name() : null)
                .site(e.getSite())
                .method(e.getMethod())
                .device(e.getDevice())
                .build();
    }
}
