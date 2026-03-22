package com.doctorclinicapp.backend.controller.encounter;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;

import com.doctorclinicapp.backend.model.encounter.ICDCode;
import com.doctorclinicapp.backend.repository.encounter.ICDCodeRepository;

@RestController
@RequestMapping("/api/icd-codes")
@RequiredArgsConstructor
public class ICDCodeController {

    private final ICDCodeRepository repository;

    @GetMapping
    public Page<ICDCode> searchICDCodes(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return repository
                .findByCodeContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        search, search, pageable);
    }
}