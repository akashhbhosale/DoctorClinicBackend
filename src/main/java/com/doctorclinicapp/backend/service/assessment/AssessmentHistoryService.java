package com.doctorclinicapp.backend.service.assessment;

import java.util.List;

import com.doctorclinicapp.backend.dto.assessment.AssessmentHistoryEntryResponse;

public interface AssessmentHistoryService {

    List<AssessmentHistoryEntryResponse> getAssessmentHistoryByPatient(Long patientId);
}
