package com.doctorclinicapp.backend.controller.assessment;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doctorclinicapp.backend.dto.assessment.AssessmentHistoryEntryResponse;
import com.doctorclinicapp.backend.service.assessment.AssessmentHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assessment/history")
@RequiredArgsConstructor
public class AssessmentHistoryController {

    private final AssessmentHistoryService assessmentHistoryService;

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AssessmentHistoryEntryResponse>> getAssessmentHistoryByPatient(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(assessmentHistoryService.getAssessmentHistoryByPatient(patientId));
    }
}
