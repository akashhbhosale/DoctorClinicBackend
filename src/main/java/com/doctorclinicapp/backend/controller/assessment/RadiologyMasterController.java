package com.doctorclinicapp.backend.controller.assessment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doctorclinicapp.backend.model.assessment.RadiologyMaster;
import com.doctorclinicapp.backend.repository.assessment.RadiologyMasterRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/radiology-master")
@RequiredArgsConstructor
public class RadiologyMasterController {

    private final RadiologyMasterRepository repository;

    @GetMapping
    public Page<RadiologyMaster> getRadiologyOrders(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return repository.findByOrderNameContainingIgnoreCase(search, pageable);
    }
}
