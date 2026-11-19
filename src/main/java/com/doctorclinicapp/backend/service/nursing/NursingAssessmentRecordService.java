package com.doctorclinicapp.backend.service.nursing;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.nursing.AddNursingAssessmentRecordRequest;
import com.doctorclinicapp.backend.dto.nursing.NursingAssessmentRecordResponse;
import com.doctorclinicapp.backend.dto.nursing.NursingInterventionEntryResponse;
import com.doctorclinicapp.backend.exception.ResourceNotFoundException;
import com.doctorclinicapp.backend.model.encounter.Encounter;
import com.doctorclinicapp.backend.model.nursing.NursingAssessmentMaster;
import com.doctorclinicapp.backend.model.nursing.NursingAssessmentRecord;
import com.doctorclinicapp.backend.model.nursing.NursingDiagnosisMaster;
import com.doctorclinicapp.backend.model.nursing.NursingInterventionEntry;
import com.doctorclinicapp.backend.model.nursing.NursingOutcomeMaster;
import com.doctorclinicapp.backend.repository.encounter.EncounterRepository;
import com.doctorclinicapp.backend.repository.nursing.NursingAssessmentRecordRepository;
import com.doctorclinicapp.backend.repository.nursing.NursingAssessmentRepository;
import com.doctorclinicapp.backend.repository.nursing.NursingDiagnosisRepository;
import com.doctorclinicapp.backend.repository.nursing.NursingOutcomeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NursingAssessmentRecordService {

    private final NursingAssessmentRecordRepository repository;
    private final EncounterRepository encounterRepo;
    private final NursingAssessmentRepository assessmentMasterRepo;
    private final NursingDiagnosisRepository diagnosisMasterRepo;
    private final NursingOutcomeRepository outcomeMasterRepo;

    public NursingAssessmentRecordResponse addAssessment(AddNursingAssessmentRecordRequest req) {
        Encounter encounter = encounterRepo.findById(req.getEncounterId())
                .orElseThrow(() -> new ResourceNotFoundException("Encounter", "id", req.getEncounterId()));

        NursingAssessmentMaster assessment = assessmentMasterRepo.findById(req.getAssessmentId())
                .orElseThrow(() -> new ResourceNotFoundException("NursingAssessment", "id", req.getAssessmentId()));

        NursingDiagnosisMaster diagnosis = diagnosisMasterRepo.findById(req.getDiagnosisId())
                .orElseThrow(() -> new ResourceNotFoundException("NursingDiagnosis", "id", req.getDiagnosisId()));

        NursingAssessmentRecord entity = new NursingAssessmentRecord();
        entity.setEncounter(encounter);
        entity.setAssessment(assessment);
        entity.setDiagnosis(diagnosis);
        entity.setOutcomeScore(req.getOutcomeScore());

        if (req.getOutcomeId() != null) {
            NursingOutcomeMaster outcome = outcomeMasterRepo.findById(req.getOutcomeId())
                    .orElseThrow(() -> new ResourceNotFoundException("NursingOutcome", "id", req.getOutcomeId()));
            entity.setOutcome(outcome);
        }

        NursingAssessmentRecord saved = repository.save(entity);
        return mapToResponse(saved);
    }

    public List<NursingAssessmentRecordResponse> getByEncounter(Long encounterId) {
        return repository.findByEncounterIdOrderByCreatedAtDesc(encounterId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // All assessment records (with nested interventions) across every
    // encounter for a patient, most recent encounter first — feeds the
    // Nursing History page.
    public List<NursingAssessmentRecordResponse> getByPatient(Long patientId) {
        return repository.findByEncounter_Patient_IdOrderByEncounter_EncounterDateDescCreatedAtDesc(patientId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteAssessment(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("NursingAssessmentRecord", "id", id);
        }
        repository.deleteById(id); // cascades to interventions (orphanRemoval)
    }

    public NursingAssessmentRecordResponse mapToResponse(NursingAssessmentRecord e) {
        List<NursingInterventionEntryResponse> interventionResponses = e.getInterventions() == null
                ? List.of()
                : e.getInterventions().stream()
                        .sorted((a, b) -> a.getInterventionTime().compareTo(b.getInterventionTime()))
                        .map(this::mapInterventionToResponse)
                        .collect(Collectors.toList());

        return NursingAssessmentRecordResponse.builder()
                .id(e.getId())
                .encounterId(e.getEncounter().getId())
                .encounterDate(e.getEncounter().getEncounterDate())
                .assessmentId(e.getAssessment().getId())
                .assessment(e.getAssessment().getName())
                .diagnosisId(e.getDiagnosis().getId())
                .diagnosis(e.getDiagnosis().getName())
                .outcomeId(e.getOutcome() != null ? e.getOutcome().getId() : null)
                .outcome(e.getOutcome() != null ? e.getOutcome().getName() : null)
                .outcomeScore(e.getOutcomeScore())
                .createdAt(e.getCreatedAt())
                .interventions(interventionResponses)
                .build();
    }

    private NursingInterventionEntryResponse mapInterventionToResponse(NursingInterventionEntry i) {
        return NursingInterventionEntryResponse.builder()
                .id(i.getId())
                .assessmentRecordId(i.getAssessmentRecord().getId())
                .interventionId(i.getIntervention().getId())
                .intervention(i.getIntervention().getName())
                .interventionTime(i.getInterventionTime())
                .note(i.getNote())
                .build();
    }
}
