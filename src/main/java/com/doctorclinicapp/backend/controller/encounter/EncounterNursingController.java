package com.doctorclinicapp.backend.controller.encounter;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.doctorclinicapp.backend.dto.encounter.*;
import com.doctorclinicapp.backend.service.encounter.EncounterNursingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/encounter-nursing")
@RequiredArgsConstructor
public class EncounterNursingController {

    private final EncounterNursingService service;

    // ADD
    @PostMapping
    public ResponseEntity<EncounterNursingResponse> addNursing(
            @Valid @RequestBody AddEncounterNursingRequest req) {

        EncounterNursingResponse response = service.addNursing(req);
        return ResponseEntity.status(201).body(response);
    }

    // GET (single encounter)
    @GetMapping("/{encounterId}")
    public List<EncounterNursingResponse> get(@PathVariable Long encounterId) {
        return service.getByEncounter(encounterId);
    }

    // GET (all nursing records for a patient, across every encounter) —
    // used by the Nursing History page. Must be mapped BEFORE /{encounterId}
    // would ever ambiguously match "patient" as an id — Spring resolves this
    // fine since "/patient/{patientId}" is a more specific literal segment,
    // but keeping it visually separate here for clarity.
    @GetMapping("/patient/{patientId}")
    public List<EncounterNursingResponse> getByPatient(@PathVariable Long patientId) {
        return service.getByPatient(patientId);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteNursing(id);
    }
}
