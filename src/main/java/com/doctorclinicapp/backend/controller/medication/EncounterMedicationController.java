package com.doctorclinicapp.backend.controller.medication;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctorclinicapp.backend.dto.medication.AddEncounterMedicationRequest;
import com.doctorclinicapp.backend.dto.medication.EncounterMedicationResponse;
import com.doctorclinicapp.backend.service.medication.EncounterMedicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/encounter-medication")
@RequiredArgsConstructor
public class EncounterMedicationController {

    private final EncounterMedicationService service;

    @PostMapping
    public ResponseEntity<EncounterMedicationResponse> addMedication(
            @Valid @RequestBody AddEncounterMedicationRequest req) {

        return ResponseEntity.status(201).body(service.addMedication(req));
    }

    @GetMapping("/{encounterId}")
    public List<EncounterMedicationResponse> get(@PathVariable Long encounterId) {
        return service.getByEncounter(encounterId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteMedication(id);
    }
}
