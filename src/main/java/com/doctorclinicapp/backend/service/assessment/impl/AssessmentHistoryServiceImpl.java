package com.doctorclinicapp.backend.service.assessment.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.assessment.AssessmentHistoryEntryResponse;
import com.doctorclinicapp.backend.dto.assessment.EncounterLaboratoryFileResponse;
import com.doctorclinicapp.backend.dto.assessment.EncounterLaboratoryResponse;
import com.doctorclinicapp.backend.dto.assessment.EncounterObservationResponse;
import com.doctorclinicapp.backend.dto.assessment.EncounterRadiologyFileResponse;
import com.doctorclinicapp.backend.dto.assessment.EncounterRadiologyResponse;
import com.doctorclinicapp.backend.dto.assessment.EncounterVitalsResponse;
import com.doctorclinicapp.backend.model.assessment.EncounterLaboratory;
import com.doctorclinicapp.backend.model.assessment.EncounterLaboratoryFile;
import com.doctorclinicapp.backend.model.assessment.EncounterObservation;
import com.doctorclinicapp.backend.model.assessment.EncounterRadiology;
import com.doctorclinicapp.backend.model.assessment.EncounterRadiologyFile;
import com.doctorclinicapp.backend.model.assessment.EncounterVitals;
import com.doctorclinicapp.backend.model.encounter.Encounter;
import com.doctorclinicapp.backend.repository.assessment.EncounterLaboratoryFileRepository;
import com.doctorclinicapp.backend.repository.assessment.EncounterLaboratoryRepository;
import com.doctorclinicapp.backend.repository.assessment.EncounterObservationRepository;
import com.doctorclinicapp.backend.repository.assessment.EncounterRadiologyFileRepository;
import com.doctorclinicapp.backend.repository.assessment.EncounterRadiologyRepository;
import com.doctorclinicapp.backend.repository.assessment.EncounterVitalsRepository;
import com.doctorclinicapp.backend.repository.encounter.EncounterRepository;
import com.doctorclinicapp.backend.service.assessment.AssessmentHistoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssessmentHistoryServiceImpl implements AssessmentHistoryService {

    private final EncounterRepository encounterRepository;
    private final EncounterVitalsRepository vitalsRepository;
    private final EncounterLaboratoryRepository laboratoryRepository;
    private final EncounterLaboratoryFileRepository laboratoryFileRepository;
    private final EncounterRadiologyRepository radiologyRepository;
    private final EncounterRadiologyFileRepository radiologyFileRepository;
    private final EncounterObservationRepository observationRepository;

    @Override
    public List<AssessmentHistoryEntryResponse> getAssessmentHistoryByPatient(Long patientId) {

        List<Encounter> encounters = encounterRepository.findByPatientIdOrderByEncounterDateDesc(patientId);

        return encounters.stream()
                .map(this::buildEntryForEncounter)
                .toList();
    }

    private AssessmentHistoryEntryResponse buildEntryForEncounter(Encounter encounter) {
        Long encounterId = encounter.getId();

        EncounterVitalsResponse vitals = vitalsRepository.findByEncounterId(encounterId)
                .map(this::mapVitals)
                .orElse(null);

        List<EncounterLaboratoryResponse> laboratory = laboratoryRepository
                .findByEncounterIdOrderByCreatedAtDesc(encounterId)
                .stream()
                .map(this::mapLaboratory)
                .toList();

        List<EncounterRadiologyResponse> radiology = radiologyRepository
                .findByEncounterIdOrderByCreatedAtDesc(encounterId)
                .stream()
                .map(this::mapRadiology)
                .toList();

        List<EncounterObservationResponse> observations = observationRepository
                .findByEncounterIdOrderByCreatedAtDesc(encounterId)
                .stream()
                .map(this::mapObservation)
                .toList();

        return AssessmentHistoryEntryResponse.builder()
                .encounterId(encounterId)
                .encounterDate(encounter.getEncounterDate())
                .vitals(vitals)
                .laboratory(laboratory)
                .radiology(radiology)
                .observations(observations)
                .build();
    }

    private EncounterVitalsResponse mapVitals(EncounterVitals v) {
        return EncounterVitalsResponse.builder()
                .id(v.getId())
                .encounterId(v.getEncounter().getId())
                .systolicBp(v.getSystolicBp())
                .diastolicBp(v.getDiastolicBp())
                .pulseRate(v.getPulseRate())
                .respiratoryRate(v.getRespiratoryRate())
                .spo2(v.getSpo2())
                .height(v.getHeight())
                .weight(v.getWeight())
                .temperature(v.getTemperature())
                .tempUnit(v.getTempUnit())
                .createdAt(v.getCreatedAt())
                .updatedAt(v.getUpdatedAt())
                .build();
    }

    private EncounterLaboratoryResponse mapLaboratory(EncounterLaboratory lab) {
        List<EncounterLaboratoryFileResponse> files = laboratoryFileRepository
                .findByLaboratoryIdOrderByUploadedAtDesc(lab.getId())
                .stream()
                .map(this::mapLaboratoryFile)
                .toList();

        return EncounterLaboratoryResponse.builder()
                .id(lab.getId())
                .encounterId(lab.getEncounter().getId())
                .laboratoryMasterId(lab.getLaboratoryMaster().getId())
                .testName(lab.getLaboratoryMaster().getTestName())
                .labResult(lab.getLabResult())
                .createdAt(lab.getCreatedAt())
                .updatedAt(lab.getUpdatedAt())
                .files(files)
                .build();
    }

    private EncounterLaboratoryFileResponse mapLaboratoryFile(EncounterLaboratoryFile f) {
        return EncounterLaboratoryFileResponse.builder()
                .id(f.getId())
                .originalFileName(f.getOriginalFileName())
                .storedFileName(f.getStoredFileName())
                .filePath(f.getFilePath())
                .contentType(f.getContentType())
                .fileSize(f.getFileSize())
                .uploadedAt(f.getUploadedAt())
                .build();
    }

    private EncounterRadiologyResponse mapRadiology(EncounterRadiology r) {
        List<EncounterRadiologyFileResponse> files = radiologyFileRepository
                .findByRadiologyIdOrderByUploadedAtDesc(r.getId())
                .stream()
                .map(this::mapRadiologyFile)
                .toList();

        return EncounterRadiologyResponse.builder()
                .id(r.getId())
                .encounterId(r.getEncounter().getId())
                .radiologyMasterId(r.getRadiologyMaster().getId())
                .orderName(r.getRadiologyMaster().getOrderName())
                .radiologyResult(r.getRadiologyResult())
                .createdAt(r.getCreatedAt())
                .updatedAt(r.getUpdatedAt())
                .files(files)
                .build();
    }

    private EncounterRadiologyFileResponse mapRadiologyFile(EncounterRadiologyFile f) {
        return EncounterRadiologyFileResponse.builder()
                .id(f.getId())
                .originalFileName(f.getOriginalFileName())
                .storedFileName(f.getStoredFileName())
                .filePath(f.getFilePath())
                .contentType(f.getContentType())
                .fileSize(f.getFileSize())
                .uploadedAt(f.getUploadedAt())
                .build();
    }

    private EncounterObservationResponse mapObservation(EncounterObservation o) {
        return EncounterObservationResponse.builder()
                .id(o.getId())
                .encounterId(o.getEncounter().getId())
                .observationMasterId(o.getObservationMaster().getId())
                .observationName(o.getObservationMaster().getObservationName())
                .assessmentResult(o.getAssessmentResult())
                .createdAt(o.getCreatedAt())
                .updatedAt(o.getUpdatedAt())
                .build();
    }
}
