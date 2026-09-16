package com.doctorclinicapp.backend.controller.assessment;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.doctorclinicapp.backend.dto.assessment.AddEncounterRadiologyRequest;
import com.doctorclinicapp.backend.dto.assessment.EncounterRadiologyFileResponse;
import com.doctorclinicapp.backend.dto.assessment.EncounterRadiologyResponse;
import com.doctorclinicapp.backend.dto.assessment.FileContentResponse;
import com.doctorclinicapp.backend.service.assessment.EncounterRadiologyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/assessment/radiology")
@RequiredArgsConstructor
public class EncounterRadiologyController {

    private final EncounterRadiologyService encounterRadiologyService;

    @PostMapping
    public ResponseEntity<EncounterRadiologyResponse> addRadiology(
            @RequestBody @Valid AddEncounterRadiologyRequest request) {

        return ResponseEntity.status(201).body(encounterRadiologyService.addRadiology(request));
    }

    @GetMapping("/{encounterId}")
    public ResponseEntity<List<EncounterRadiologyResponse>> getRadiologyByEncounterId(
            @PathVariable Long encounterId) {

        return ResponseEntity.ok(encounterRadiologyService.getRadiologyByEncounterId(encounterId));
    }

    @DeleteMapping("/{radiologyId}")
    public ResponseEntity<Void> deleteRadiology(@PathVariable Long radiologyId) {
        encounterRadiologyService.deleteRadiology(radiologyId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{radiologyId}/files")
    public ResponseEntity<EncounterRadiologyFileResponse> uploadRadiologyFile(
            @PathVariable Long radiologyId,
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.status(201)
                .body(encounterRadiologyService.uploadRadiologyFile(radiologyId, file));
    }

    @GetMapping("/{radiologyId}/files")
    public ResponseEntity<List<EncounterRadiologyFileResponse>> getFilesByRadiologyId(
            @PathVariable Long radiologyId) {

        return ResponseEntity.ok(encounterRadiologyService.getFilesByRadiologyId(radiologyId));
    }

    @DeleteMapping("/files/{fileId}")
    public ResponseEntity<Void> deleteRadiologyFile(@PathVariable Long fileId) {
        encounterRadiologyService.deleteRadiologyFile(fileId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/files/{fileId}/view")
    public ResponseEntity<byte[]> viewRadiologyFile(@PathVariable Long fileId) {
        FileContentResponse content = encounterRadiologyService.downloadRadiologyFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(content.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + content.getFileName() + "\"")
                .body(content.getData());
    }
}
