package com.doctorclinicapp.backend.service.nursing;

import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.nursing.AddNursingInterventionRequest;
import com.doctorclinicapp.backend.dto.nursing.NursingInterventionEntryResponse;
import com.doctorclinicapp.backend.exception.ResourceNotFoundException;
import com.doctorclinicapp.backend.model.nursing.NursingAssessmentRecord;
import com.doctorclinicapp.backend.model.nursing.NursingInterventionEntry;
import com.doctorclinicapp.backend.model.nursing.NursingInterventionMaster;
import com.doctorclinicapp.backend.repository.nursing.NursingAssessmentRecordRepository;
import com.doctorclinicapp.backend.repository.nursing.NursingInterventionEntryRepository;
import com.doctorclinicapp.backend.repository.nursing.NursingInterventionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NursingInterventionEntryService {

    private final NursingInterventionEntryRepository repository;
    private final NursingAssessmentRecordRepository assessmentRecordRepo;
    private final NursingInterventionRepository interventionMasterRepo;

    public NursingInterventionEntryResponse addIntervention(AddNursingInterventionRequest req) {
        NursingAssessmentRecord assessmentRecord = assessmentRecordRepo.findById(req.getAssessmentRecordId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "NursingAssessmentRecord", "id", req.getAssessmentRecordId()));

        NursingInterventionMaster intervention = interventionMasterRepo.findById(req.getInterventionId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "NursingIntervention", "id", req.getInterventionId()));

        NursingInterventionEntry entity = new NursingInterventionEntry();
        entity.setAssessmentRecord(assessmentRecord);
        entity.setIntervention(intervention);
        entity.setInterventionTime(req.getInterventionTime()); // null is fine — @PrePersist defaults to now
        entity.setNote(req.getNote());

        NursingInterventionEntry saved = repository.save(entity);

        return NursingInterventionEntryResponse.builder()
                .id(saved.getId())
                .assessmentRecordId(assessmentRecord.getId())
                .interventionId(intervention.getId())
                .intervention(intervention.getName())
                .interventionTime(saved.getInterventionTime())
                .note(saved.getNote())
                .build();
    }

    public void deleteIntervention(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("NursingInterventionEntry", "id", id);
        }
        repository.deleteById(id);
    }
}
