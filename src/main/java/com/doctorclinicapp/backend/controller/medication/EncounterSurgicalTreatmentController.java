package com.doctorclinicapp.backend.controller.medication;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctorclinicapp.backend.dto.medication.AddEncounterSurgicalTreatmentRequest;
import com.doctorclinicapp.backend.dto.medication.EncounterSurgicalTreatmentResponse;
import com.doctorclinicapp.backend.service.medication.EncounterSurgicalTreatmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/encounter-surgical-treatment")
@RequiredArgsConstructor
public class EncounterSurgicalTreatmentController {

    private final EncounterSurgicalTreatmentService service;

    @PostMapping
    public ResponseEntity<EncounterSurgicalTreatmentResponse> addSurgicalTreatment(
            @Valid @RequestBody AddEncounterSurgicalTreatmentRequest req) {

        return ResponseEntity.status(201).body(service.addSurgicalTreatment(req));
    }

    @GetMapping("/{encounterId}")
    public List<EncounterSurgicalTreatmentResponse> get(@PathVariable Long encounterId) {
        return service.getByEncounter(encounterId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteSurgicalTreatment(id);
    }
}
