package com.doctorclinicapp.backend.service.medication;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.medication.AddEncounterMedicationRequest;
import com.doctorclinicapp.backend.dto.medication.EncounterMedicationResponse;
import com.doctorclinicapp.backend.exception.ResourceNotFoundException;
import com.doctorclinicapp.backend.model.encounter.Encounter;
import com.doctorclinicapp.backend.model.medication.DirectionOfUseMaster;
import com.doctorclinicapp.backend.model.medication.DoseFormMaster;
import com.doctorclinicapp.backend.model.medication.EncounterMedication;
import com.doctorclinicapp.backend.model.medication.MedicineMaster;
import com.doctorclinicapp.backend.model.medication.RouteOfAdministrationMaster;
import com.doctorclinicapp.backend.repository.encounter.EncounterRepository;
import com.doctorclinicapp.backend.repository.medication.DirectionOfUseMasterRepository;
import com.doctorclinicapp.backend.repository.medication.DoseFormMasterRepository;
import com.doctorclinicapp.backend.repository.medication.EncounterMedicationRepository;
import com.doctorclinicapp.backend.repository.medication.MedicineMasterRepository;
import com.doctorclinicapp.backend.repository.medication.RouteOfAdministrationMasterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncounterMedicationService {

    private final EncounterMedicationRepository repository;
    private final EncounterRepository encounterRepo;
    private final MedicineMasterRepository medicineRepo;
    private final DoseFormMasterRepository doseFormRepo;
    private final RouteOfAdministrationMasterRepository routeRepo;
    private final DirectionOfUseMasterRepository directionRepo;

    public EncounterMedicationResponse addMedication(AddEncounterMedicationRequest req) {
        Encounter encounter = encounterRepo.findById(req.getEncounterId())
                .orElseThrow(() -> new ResourceNotFoundException("Encounter", "id", req.getEncounterId()));

        MedicineMaster medicine = medicineRepo.findById(req.getMedicineMasterId())
                .orElseThrow(() -> new ResourceNotFoundException("Medicine", "id", req.getMedicineMasterId()));

        DirectionOfUseMaster direction = directionRepo.findById(req.getDirectionOfUseId())
                .orElseThrow(() -> new ResourceNotFoundException("DirectionOfUse", "id", req.getDirectionOfUseId()));

        EncounterMedication entity = new EncounterMedication();
        entity.setEncounter(encounter);
        entity.setMedicineMaster(medicine);
        entity.setDirectionOfUse(direction);
        entity.setDosage(req.getDosage());
        entity.setDurationDays(req.getDurationDays());
        entity.setComment(req.getComment());

        if (req.getDoseFormId() != null) {
            DoseFormMaster doseForm = doseFormRepo.findById(req.getDoseFormId())
                    .orElseThrow(() -> new ResourceNotFoundException("DoseForm", "id", req.getDoseFormId()));
            entity.setDoseForm(doseForm);
        }

        if (req.getRouteOfAdministrationId() != null) {
            RouteOfAdministrationMaster route = routeRepo.findById(req.getRouteOfAdministrationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Route", "id", req.getRouteOfAdministrationId()));
            entity.setRouteOfAdministration(route);
        }

        EncounterMedication saved = repository.save(entity);
        return mapToResponse(saved);
    }

    public List<EncounterMedicationResponse> getByEncounter(Long encounterId) {
        return repository.findByEncounterIdOrderByCreatedAtDesc(encounterId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteMedication(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("EncounterMedication", "id", id);
        }
        repository.deleteById(id);
    }

    private EncounterMedicationResponse mapToResponse(EncounterMedication e) {
        return EncounterMedicationResponse.builder()
                .id(e.getId())
                .encounterId(e.getEncounter().getId())
                .medicineMasterId(e.getMedicineMaster().getId())
                .medicineName(e.getMedicineMaster().getMedicineName())
                .medicineType(e.getMedicineMaster().getMedicineType().name())
                .doseFormId(e.getDoseForm() != null ? e.getDoseForm().getId() : null)
                .doseForm(e.getDoseForm() != null ? e.getDoseForm().getDoseFormName() : null)
                .routeOfAdministrationId(e.getRouteOfAdministration() != null ? e.getRouteOfAdministration().getId() : null)
                .routeOfAdministration(e.getRouteOfAdministration() != null ? e.getRouteOfAdministration().getRouteName() : null)
                .directionOfUseId(e.getDirectionOfUse().getId())
                .directionOfUse(e.getDirectionOfUse().getDirectionName())
                .dosage(e.getDosage())
                .durationDays(e.getDurationDays())
                .comment(e.getComment())
                .build();
    }
}
