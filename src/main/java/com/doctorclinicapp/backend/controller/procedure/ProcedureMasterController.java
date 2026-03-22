package com.doctorclinicapp.backend.controller.procedure;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.procedure.ProcedureMasterResponse;
import com.doctorclinicapp.backend.service.procedure.ProcedureMasterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/procedures")
@RequiredArgsConstructor
public class ProcedureMasterController {

    private final ProcedureMasterService service;

    @GetMapping
    public ResponseEntity<PageResponse<ProcedureMasterResponse>> getProcedures(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return ResponseEntity.ok(service.getProcedures(page, size, sortBy, sortDir));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<ProcedureMasterResponse>> searchProcedures(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return ResponseEntity.ok(service.searchProcedures(keyword, page, size));
    }
}