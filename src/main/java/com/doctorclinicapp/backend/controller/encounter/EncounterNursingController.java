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

import com.doctorclinicapp.backend.dto.encounter.AddEncounterNursingRequest;
import com.doctorclinicapp.backend.dto.encounter.EncounterNursingResponse;
import com.doctorclinicapp.backend.service.encounter.EncounterNursingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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

    // GET
    @GetMapping("/{encounterId}")
    public List<EncounterNursingResponse> get(@PathVariable Long encounterId) {
        return service.getByEncounter(encounterId);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteNursing(id);
    }
}