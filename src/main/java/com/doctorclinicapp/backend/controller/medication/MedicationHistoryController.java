package com.doctorclinicapp.backend.controller.medication;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doctorclinicapp.backend.dto.medication.MedicationHistoryEntryResponse;
import com.doctorclinicapp.backend.service.medication.MedicationHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/medication-history")
@RequiredArgsConstructor
public class MedicationHistoryController {

    private final MedicationHistoryService medicationHistoryService;

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicationHistoryEntryResponse>> getMedicationHistoryByPatient(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(medicationHistoryService.getMedicationHistoryByPatient(patientId));
    }
}
