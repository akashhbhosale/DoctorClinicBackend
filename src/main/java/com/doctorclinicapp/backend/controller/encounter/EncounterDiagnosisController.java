package com.doctorclinicapp.backend.controller.encounter;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doctorclinicapp.backend.dto.encounter.AddEncounterDiagnosisRequest;
import com.doctorclinicapp.backend.dto.encounter.EncounterDiagnosisResponse;
import com.doctorclinicapp.backend.model.encounter.EncounterDiagnosis;
import com.doctorclinicapp.backend.service.encounter.EncounterDiagnosisService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/encounter-diagnosis")
@RequiredArgsConstructor

public class EncounterDiagnosisController {

    private final EncounterDiagnosisService service;
    @PostMapping
    public ResponseEntity<EncounterDiagnosis> addDiagnosis(
            @RequestBody @Valid AddEncounterDiagnosisRequest request) {

        return ResponseEntity.status(201).body(service.addDiagnosis(request));
    }
    
    @GetMapping("/{encounterId}")
    public List<EncounterDiagnosisResponse> getDiagnosis(
            @PathVariable Long encounterId) {

        return service.getDiagnosesByEncounterId(encounterId);
    }
    
    //Delete
    @DeleteMapping("/{id}")
    public void deleteDiagnosis(@PathVariable Long id) {
        service.deleteDiagnosis(id);
    }
}