package com.doctorclinicapp.backend.controller.encounter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import com.doctorclinicapp.backend.dto.encounter.CreateEncounterRequest;
import com.doctorclinicapp.backend.dto.encounter.DeleteEncounterResponse;
import com.doctorclinicapp.backend.dto.encounter.EncounterDetailsResponse;
import com.doctorclinicapp.backend.dto.encounter.UpdateEncounterNotesRequest;
import com.doctorclinicapp.backend.model.encounter.Encounter;
import com.doctorclinicapp.backend.service.encounter.EncounterService;
import java.util.List;
import com.doctorclinicapp.backend.dto.encounter.EncounterHistoryResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/encounters")
@RequiredArgsConstructor
public class EncounterController {

    private final EncounterService encounterService;

    @PostMapping
    public ResponseEntity<Encounter> createEncounter(
            @RequestBody @Valid CreateEncounterRequest request) {

        Encounter encounter = encounterService.createEncounter(
                request.getPatientId(),
                request.getDoctorId());

        return ResponseEntity.status(201).body(encounter);
    }
    
    //Encounter Section Code
    @PutMapping("/{encounterId}/notes")
    public Encounter updateNotes(
            @PathVariable Long encounterId,
            @RequestBody @Valid UpdateEncounterNotesRequest request) {

        return encounterService.updateNotes(encounterId, request.getNotes());
    }
    
    //Get
    @GetMapping("/{id}")
    public ResponseEntity<Encounter> getEncounterById(@PathVariable Long id) {
        return ResponseEntity.ok(encounterService.getEncounterById(id));
    }
    
    //Show All
    @GetMapping("/{id}/details")
    public ResponseEntity<EncounterDetailsResponse> getEncounterDetails(@PathVariable Long id) {
        return ResponseEntity.ok(encounterService.getEncounterDetails(id));
    }
    
    // Delete All
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteEncounterResponse> deleteEncounter(@PathVariable Long id) {
        return ResponseEntity.ok(encounterService.deleteEncounter(id));
    }
    
    // To get history of encounters
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<EncounterHistoryResponse>> getEncounterHistoryByPatient(
            @PathVariable Long patientId) {
        return ResponseEntity.ok(encounterService.getEncounterHistoryByPatientId(patientId));
    }
}