package com.doctorclinicapp.backend.controller.encounter;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.doctorclinicapp.backend.dto.encounter.*;
import com.doctorclinicapp.backend.model.encounter.EncounterProcedure;
import com.doctorclinicapp.backend.service.encounter.EncounterProcedureService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/encounter-procedure")
@RequiredArgsConstructor
public class EncounterProcedureController {

    private final EncounterProcedureService service;

    // ADD
        @PostMapping
    public ResponseEntity<EncounterProcedureResponse> addProcedure(
            @Valid @RequestBody AddEncounterProcedureRequest req) {

        EncounterProcedureResponse response = service.addProcedure(req);
        return ResponseEntity.status(201).body(response);
    }


    // GET
    @GetMapping("/{encounterId}")
    public List<EncounterProcedureResponse> get(@PathVariable Long encounterId) {
        return service.getByEncounter(encounterId);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteProcedure(id);
    }
    
    }