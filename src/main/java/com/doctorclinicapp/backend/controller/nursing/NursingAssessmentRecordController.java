package com.doctorclinicapp.backend.controller.nursing;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctorclinicapp.backend.dto.nursing.AddNursingAssessmentRecordRequest;
import com.doctorclinicapp.backend.dto.nursing.NursingAssessmentRecordResponse;
import com.doctorclinicapp.backend.service.nursing.NursingAssessmentRecordService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/nursing-assessment-record")
@RequiredArgsConstructor
public class NursingAssessmentRecordController {

    private final NursingAssessmentRecordService service;

    @PostMapping
    public ResponseEntity<NursingAssessmentRecordResponse> addAssessment(
            @Valid @RequestBody AddNursingAssessmentRecordRequest req) {

        return ResponseEntity.status(201).body(service.addAssessment(req));
    }

    @GetMapping("/{encounterId}")
    public List<NursingAssessmentRecordResponse> get(@PathVariable Long encounterId) {
        return service.getByEncounter(encounterId);
    }

    @GetMapping("/patient/{patientId}")
    public List<NursingAssessmentRecordResponse> getByPatient(@PathVariable Long patientId) {
        return service.getByPatient(patientId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteAssessment(id);
    }
}
