package com.doctorclinicapp.backend.controller.nursing;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.nursing.NursingInterventionResponse;
import com.doctorclinicapp.backend.service.nursing.NursingInterventionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/nursing-interventions")
@RequiredArgsConstructor
public class NursingInterventionController {

    private final NursingInterventionService service;

    @GetMapping
    public ResponseEntity<PageResponse<NursingInterventionResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.getAll(page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<NursingInterventionResponse>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.search(keyword, page, size));
    }
}
