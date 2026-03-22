package com.doctorclinicapp.backend.controller.procedure;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.procedure.ProcedureMethodResponse;
import com.doctorclinicapp.backend.service.procedure.ProcedureMethodService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/procedure-methods")
@RequiredArgsConstructor
public class ProcedureMethodController {

    private final ProcedureMethodService service;

    @GetMapping
    public ResponseEntity<PageResponse<ProcedureMethodResponse>> getMethods(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.getMethods(page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<ProcedureMethodResponse>> searchMethods(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.searchMethods(keyword, page, size));
    }
}