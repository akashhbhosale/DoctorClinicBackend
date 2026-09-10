package com.doctorclinicapp.backend.controller.encounter;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doctorclinicapp.backend.dto.encounter.AddEncounterProcedureRequest;
import com.doctorclinicapp.backend.dto.encounter.EncounterProcedureResponse;
import com.doctorclinicapp.backend.service.encounter.EncounterProcedureService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/encounter-procedure")
@RequiredArgsConstructor
public class EncounterProcedureController {

    private final EncounterProcedureService service;

    // ADD
        @PostMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
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
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    public void delete(@PathVariable Long id) {
        service.deleteProcedure(id);
    }
    
    }