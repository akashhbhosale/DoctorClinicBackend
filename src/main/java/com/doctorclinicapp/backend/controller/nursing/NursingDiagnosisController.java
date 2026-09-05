package com.doctorclinicapp.backend.controller.nursing;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.nursing.NursingDiagnosisResponse;
import com.doctorclinicapp.backend.service.nursing.NursingDiagnosisService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/nursing-diagnoses")
@RequiredArgsConstructor
public class NursingDiagnosisController {

    private final NursingDiagnosisService service;

    @GetMapping
    public ResponseEntity<PageResponse<NursingDiagnosisResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.getAll(page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<NursingDiagnosisResponse>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.search(keyword, page, size));
    }
}
