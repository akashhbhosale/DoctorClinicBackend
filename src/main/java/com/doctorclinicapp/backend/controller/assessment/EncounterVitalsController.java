package com.doctorclinicapp.backend.controller.assessment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctorclinicapp.backend.dto.assessment.EncounterVitalsResponse;
import com.doctorclinicapp.backend.dto.assessment.SaveEncounterVitalsRequest;
import com.doctorclinicapp.backend.service.assessment.EncounterVitalsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assessment/vitals")
@RequiredArgsConstructor
public class EncounterVitalsController {

    private final EncounterVitalsService encounterVitalsService;

    @PutMapping("/{encounterId}")
    public ResponseEntity<EncounterVitalsResponse> saveOrUpdateVitals(
            @PathVariable Long encounterId,
            @RequestBody @Valid SaveEncounterVitalsRequest request) {

        return ResponseEntity.ok(encounterVitalsService.saveOrUpdateVitals(encounterId, request));
    }

    @GetMapping("/{encounterId}")
    public ResponseEntity<EncounterVitalsResponse> getVitalsByEncounterId(@PathVariable Long encounterId) {
        return ResponseEntity.ok(encounterVitalsService.getVitalsByEncounterId(encounterId));
    }

    @DeleteMapping("/{encounterId}")
    public ResponseEntity<String> deleteVitalsByEncounterId(@PathVariable Long encounterId) {
        encounterVitalsService.deleteVitalsByEncounterId(encounterId);
        return ResponseEntity.ok("Vitals deleted successfully for encounter id: " + encounterId);
    }
}