package com.doctorclinicapp.backend.controller.assessment;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctorclinicapp.backend.dto.assessment.AddEncounterObservationRequest;
import com.doctorclinicapp.backend.dto.assessment.EncounterObservationResponse;
import com.doctorclinicapp.backend.service.assessment.EncounterObservationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assessment/observation")
@RequiredArgsConstructor
public class EncounterObservationController {

    private final EncounterObservationService encounterObservationService;

    @PostMapping
    public ResponseEntity<EncounterObservationResponse> addObservation(
            @RequestBody @Valid AddEncounterObservationRequest request) {

        return ResponseEntity.status(201).body(encounterObservationService.addObservation(request));
    }

    @GetMapping("/{encounterId}")
    public ResponseEntity<List<EncounterObservationResponse>> getObservationsByEncounterId(
            @PathVariable Long encounterId) {

        return ResponseEntity.ok(encounterObservationService.getObservationsByEncounterId(encounterId));
    }

    @DeleteMapping("/{observationId}")
    public ResponseEntity<Void> deleteObservation(@PathVariable Long observationId) {
        encounterObservationService.deleteObservation(observationId);
        return ResponseEntity.noContent().build();
    }
}
