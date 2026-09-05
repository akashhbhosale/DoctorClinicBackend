package com.doctorclinicapp.backend.service.encounter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.doctorclinicapp.backend.dto.encounter.*;
import com.doctorclinicapp.backend.exception.ResourceNotFoundException;
import com.doctorclinicapp.backend.model.encounter.*;
import com.doctorclinicapp.backend.model.nursing.*;
import com.doctorclinicapp.backend.repository.encounter.*;
import com.doctorclinicapp.backend.repository.nursing.*;

@Service
@RequiredArgsConstructor
public class EncounterNursingService {

    private final EncounterNursingRepository repository;
    private final EncounterRepository encounterRepo;
    private final NursingAssessmentRepository assessmentRepo;
    private final NursingDiagnosisRepository diagnosisRepo;
    private final NursingOutcomeRepository outcomeRepo;
    private final NursingInterventionRepository interventionRepo;

    // 🔥 ADD NURSING RECORD
    public EncounterNursingResponse addNursing(AddEncounterNursingRequest req) {

        Encounter encounter = encounterRepo.findById(req.getEncounterId())
                .orElseThrow(() -> new ResourceNotFoundException("Encounter", "id", req.getEncounterId()));

        NursingAssessmentMaster assessment = assessmentRepo.findById(req.getAssessmentId())
                .orElseThrow(() -> new ResourceNotFoundException("NursingAssessment", "id", req.getAssessmentId()));

        NursingDiagnosisMaster diagnosis = diagnosisRepo.findById(req.getDiagnosisId())
                .orElseThrow(() -> new ResourceNotFoundException("NursingDiagnosis", "id", req.getDiagnosisId()));

        NursingInterventionMaster intervention = interventionRepo.findById(req.getInterventionId())
                .orElseThrow(() -> new ResourceNotFoundException("NursingIntervention", "id", req.getInterventionId()));

        EncounterNursing entity = new EncounterNursing();
        entity.setEncounter(encounter);
        entity.setAssessment(assessment);
        entity.setDiagnosis(diagnosis);
        entity.setIntervention(intervention);

        // OPTIONAL FIELDS
        if (req.getOutcomeId() != null) {
            entity.setOutcome(outcomeRepo.findById(req.getOutcomeId()).orElse(null));
        }
        entity.setOutcomeScore(req.getOutcomeScore());

        EncounterNursing saved = repository.save(entity);

        return mapToResponse(saved);
    }

    // 🔥 GET BY ENCOUNTER
    public List<EncounterNursingResponse> getByEncounter(Long encounterId) {

        return repository.findByEncounterId(encounterId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // DELETE
    public void deleteNursing(Long id) {

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("EncounterNursing", "id", id);
        }

        repository.deleteById(id);
    }

    private EncounterNursingResponse mapToResponse(EncounterNursing e) {
        return EncounterNursingResponse.builder()
                .id(e.getId())
                .assessmentId(e.getAssessment().getId())
                .assessment(e.getAssessment().getName())
                .diagnosisId(e.getDiagnosis().getId())
                .diagnosis(e.getDiagnosis().getName())
                .outcomeId(e.getOutcome() != null ? e.getOutcome().getId() : null)
                .outcome(e.getOutcome() != null ? e.getOutcome().getName() : null)
                .outcomeScore(e.getOutcomeScore())
                .interventionId(e.getIntervention().getId())
                .intervention(e.getIntervention().getName())
                .build();
    }
}
