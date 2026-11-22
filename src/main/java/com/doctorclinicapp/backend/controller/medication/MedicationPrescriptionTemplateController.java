
package com.doctorclinicapp.backend.controller.medication;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doctorclinicapp.backend.dto.medication.CreatePrescriptionTemplateRequest;
import com.doctorclinicapp.backend.dto.medication.PrescriptionTemplateResponse;
import com.doctorclinicapp.backend.service.medication.MedicationPrescriptionTemplateService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/prescription-templates")
@RequiredArgsConstructor
public class MedicationPrescriptionTemplateController {

    private final MedicationPrescriptionTemplateService templateService;

    // Create prescription template
    @PostMapping
    public ResponseEntity<PrescriptionTemplateResponse> createTemplate(
            @Valid @RequestBody CreatePrescriptionTemplateRequest request,
            Authentication authentication) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(templateService.createTemplate(
                        request, authentication.getName()));
    }

    // Get all templates of logged-in doctor
    @GetMapping
    public ResponseEntity<List<PrescriptionTemplateResponse>> getMyTemplates(
            Authentication authentication) {

        return ResponseEntity.ok(
                templateService.getMyTemplates(authentication.getName()));
    }

    // Get template by ID
    @GetMapping("/{templateId}")
    public ResponseEntity<PrescriptionTemplateResponse> getTemplateById(
            @PathVariable Long templateId,
            Authentication authentication) {

        return ResponseEntity.ok(
                templateService.getTemplateById(
                        templateId, authentication.getName()));
    }

    // Update template
    @PutMapping("/{templateId}")
    public ResponseEntity<PrescriptionTemplateResponse> updateTemplate(
            @PathVariable Long templateId,
            @Valid @RequestBody CreatePrescriptionTemplateRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(
                templateService.updateTemplate(
                        templateId, request, authentication.getName()));
    }

    // Delete template
    @DeleteMapping("/{templateId}")
    public ResponseEntity<Void> deleteTemplate(
            @PathVariable Long templateId,
            Authentication authentication) {

        templateService.deleteTemplate(
                templateId, authentication.getName());

        return ResponseEntity.noContent().build();
    }
}
