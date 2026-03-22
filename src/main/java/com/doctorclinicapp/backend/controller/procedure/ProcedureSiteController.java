package com.doctorclinicapp.backend.controller.procedure;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.procedure.ProcedureSiteResponse;
import com.doctorclinicapp.backend.service.procedure.ProcedureSiteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/procedure-sites")
@RequiredArgsConstructor
public class ProcedureSiteController {

    private final ProcedureSiteService service;

    @GetMapping
    public ResponseEntity<PageResponse<ProcedureSiteResponse>> getSites(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.getSites(page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<ProcedureSiteResponse>> searchSites(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.searchSites(keyword, page, size));
    }
}