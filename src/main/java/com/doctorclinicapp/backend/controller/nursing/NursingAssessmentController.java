package com.doctorclinicapp.backend.controller.nursing;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.nursing.NursingAssessmentResponse;
import com.doctorclinicapp.backend.service.nursing.NursingAssessmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/nursing-assessments")
@RequiredArgsConstructor
public class NursingAssessmentController {

    private final NursingAssessmentService service;

    @GetMapping
    public ResponseEntity<PageResponse<NursingAssessmentResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.getAll(page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<NursingAssessmentResponse>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.search(keyword, page, size));
    }
}
