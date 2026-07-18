package com.doctorclinicapp.backend.controller.assessment;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.doctorclinicapp.backend.dto.assessment.AddEncounterLaboratoryRequest;
import com.doctorclinicapp.backend.dto.assessment.EncounterLaboratoryFileResponse;
import com.doctorclinicapp.backend.dto.assessment.EncounterLaboratoryResponse;
import com.doctorclinicapp.backend.service.assessment.EncounterLaboratoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assessment/laboratory")
@RequiredArgsConstructor
public class EncounterLaboratoryController {

    private final EncounterLaboratoryService encounterLaboratoryService;

    @PostMapping
    public ResponseEntity<EncounterLaboratoryResponse> addLaboratory(
            @RequestBody @Valid AddEncounterLaboratoryRequest request) {

        return ResponseEntity.status(201).body(encounterLaboratoryService.addLaboratory(request));
    }

    @GetMapping("/{encounterId}")
    public ResponseEntity<List<EncounterLaboratoryResponse>> getLaboratoryByEncounterId(
            @PathVariable Long encounterId) {

        return ResponseEntity.ok(encounterLaboratoryService.getLaboratoryByEncounterId(encounterId));
    }

    @DeleteMapping("/{laboratoryId}")
    public ResponseEntity<Void> deleteLaboratory(@PathVariable Long laboratoryId) {
        encounterLaboratoryService.deleteLaboratory(laboratoryId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{laboratoryId}/files")
    public ResponseEntity<EncounterLaboratoryFileResponse> uploadLaboratoryFile(
            @PathVariable Long laboratoryId,
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.status(201)
                .body(encounterLaboratoryService.uploadLaboratoryFile(laboratoryId, file));
    }

    @GetMapping("/{laboratoryId}/files")
    public ResponseEntity<List<EncounterLaboratoryFileResponse>> getFilesByLaboratoryId(
            @PathVariable Long laboratoryId) {

        return ResponseEntity.ok(encounterLaboratoryService.getFilesByLaboratoryId(laboratoryId));
    }

    @DeleteMapping("/files/{fileId}")
    public ResponseEntity<Void> deleteLaboratoryFile(@PathVariable Long fileId) {
        encounterLaboratoryService.deleteLaboratoryFile(fileId);
        return ResponseEntity.noContent().build();
    }
}