package com.doctorclinicapp.backend.controller.procedure;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.procedure.ProcedureDeviceResponse;
import com.doctorclinicapp.backend.service.procedure.ProcedureDeviceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/procedure-devices")
@RequiredArgsConstructor
public class ProcedureDeviceController {

    private final ProcedureDeviceService service;

    // 🔥 GET ALL (PAGINATION)
    @GetMapping
    public ResponseEntity<PageResponse<ProcedureDeviceResponse>> getDevices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return ResponseEntity.ok(service.getDevices(page, size));
    }

    // 🔥 SEARCH
    @GetMapping("/search")
    public ResponseEntity<PageResponse<ProcedureDeviceResponse>> searchDevices(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return ResponseEntity.ok(service.searchDevices(keyword, page, size));
    }
}