package com.doctorclinicapp.backend.service.medication.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.medication.EncounterFollowUpResponse;
import com.doctorclinicapp.backend.dto.medication.EncounterMedicationResponse;
import com.doctorclinicapp.backend.dto.medication.EncounterSurgicalTreatmentResponse;
import com.doctorclinicapp.backend.dto.medication.MedicationHistoryEntryResponse;
import com.doctorclinicapp.backend.model.encounter.Encounter;
import com.doctorclinicapp.backend.model.medication.EncounterFollowUp;
import com.doctorclinicapp.backend.model.medication.EncounterMedication;
import com.doctorclinicapp.backend.model.medication.EncounterSurgicalTreatment;
import com.doctorclinicapp.backend.repository.encounter.EncounterRepository;
import com.doctorclinicapp.backend.repository.medication.EncounterFollowUpRepository;
import com.doctorclinicapp.backend.repository.medication.EncounterMedicationRepository;
import com.doctorclinicapp.backend.repository.medication.EncounterSurgicalTreatmentRepository;
import com.doctorclinicapp.backend.service.medication.MedicationHistoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicationHistoryServiceImpl implements MedicationHistoryService {

    private final EncounterRepository encounterRepository;
    private final EncounterMedicationRepository medicationRepository;
    private final EncounterSurgicalTreatmentRepository surgicalTreatmentRepository;
    private final EncounterFollowUpRepository followUpRepository;

    @Override
    public List<MedicationHistoryEntryResponse> getMedicationHistoryByPatient(Long patientId) {

        List<Encounter> encounters = encounterRepository.findByPatientIdOrderByEncounterDateDesc(patientId);

        return encounters.stream()
                .map(this::buildEntryForEncounter)
                .toList();
    }

    private MedicationHistoryEntryResponse buildEntryForEncounter(Encounter encounter) {
        Long encounterId = encounter.getId();

        List<EncounterMedicationResponse> medications = medicationRepository
                .findByEncounterIdOrderByCreatedAtDesc(encounterId)
                .stream()
                .map(this::mapMedication)
                .toList();

        List<EncounterSurgicalTreatmentResponse> surgicalTreatments = surgicalTreatmentRepository
                .findByEncounterIdOrderByCreatedAtDesc(encounterId)
                .stream()
                .map(this::mapSurgicalTreatment)
                .toList();

        EncounterFollowUpResponse followUp = followUpRepository.findByEncounterId(encounterId)
                .map(this::mapFollowUp)
                .orElse(null);

        return MedicationHistoryEntryResponse.builder()
                .encounterId(encounterId)
                .encounterDate(encounter.getEncounterDate())
                .medications(medications)
                .surgicalTreatments(surgicalTreatments)
                .followUp(followUp)
                .build();
    }

    private EncounterMedicationResponse mapMedication(EncounterMedication e) {
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

    private EncounterSurgicalTreatmentResponse mapSurgicalTreatment(EncounterSurgicalTreatment e) {
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

    private EncounterFollowUpResponse mapFollowUp(EncounterFollowUp e) {
        return EncounterFollowUpResponse.builder()
                .id(e.getId())
                .encounterId(e.getEncounter().getId())
                .followUpDate(e.getFollowUpDate())
                .followUpInstructions(e.getFollowUpInstructions())
                .build();
    }
}
