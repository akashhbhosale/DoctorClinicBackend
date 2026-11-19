package com.doctorclinicapp.backend.controller.nursing;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctorclinicapp.backend.dto.nursing.AddNursingInterventionRequest;
import com.doctorclinicapp.backend.dto.nursing.NursingInterventionEntryResponse;
import com.doctorclinicapp.backend.service.nursing.NursingInterventionEntryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// Named "...EntryController" and mapped to /api/nursing-intervention-entry
// so it can't collide with the existing master-list NursingInterventionController
// (/api/nursing-interventions) that feeds the Intervention dropdown.
@RestController
@RequestMapping("/api/nursing-intervention-entry")
@RequiredArgsConstructor
public class NursingInterventionEntryController {

    private final NursingInterventionEntryService service;

    @PostMapping
    public ResponseEntity<NursingInterventionEntryResponse> addIntervention(
            @Valid @RequestBody AddNursingInterventionRequest req) {

        return ResponseEntity.status(201).body(service.addIntervention(req));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteIntervention(id);
    }
}
