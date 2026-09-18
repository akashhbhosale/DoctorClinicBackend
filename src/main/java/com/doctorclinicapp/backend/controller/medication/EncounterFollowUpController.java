package com.doctorclinicapp.backend.controller.medication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctorclinicapp.backend.dto.medication.EncounterFollowUpRequest;
import com.doctorclinicapp.backend.dto.medication.EncounterFollowUpResponse;
import com.doctorclinicapp.backend.service.medication.EncounterFollowUpService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/encounter-follow-up")
@RequiredArgsConstructor
public class EncounterFollowUpController {

    private final EncounterFollowUpService service;

    @PutMapping("/{encounterId}")
    public ResponseEntity<EncounterFollowUpResponse> save(
            @PathVariable Long encounterId,
            @RequestBody EncounterFollowUpRequest req) {

        return ResponseEntity.ok(service.saveFollowUp(encounterId, req));
    }

    @GetMapping("/{encounterId}")
    public ResponseEntity<EncounterFollowUpResponse> get(@PathVariable Long encounterId) {
        EncounterFollowUpResponse response = service.getByEncounter(encounterId);
        if (response == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }
}
