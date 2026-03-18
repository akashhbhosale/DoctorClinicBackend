package com.doctorclinicapp.backend.controller.encounter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import com.doctorclinicapp.backend.dto.encounter.CreateEncounterRequest;
import com.doctorclinicapp.backend.dto.encounter.UpdateEncounterNotesRequest;
import com.doctorclinicapp.backend.model.encounter.Encounter;
import com.doctorclinicapp.backend.service.encounter.EncounterService;

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

        return ResponseEntity.ok(encounter);
    }
    
    //Encounter Section Code
    @PutMapping("/{encounterId}/notes")
    public Encounter updateNotes(
            @PathVariable Long encounterId,
            @RequestBody @Valid UpdateEncounterNotesRequest request) {

        return encounterService.updateNotes(encounterId, request.getNotes());
    }
}