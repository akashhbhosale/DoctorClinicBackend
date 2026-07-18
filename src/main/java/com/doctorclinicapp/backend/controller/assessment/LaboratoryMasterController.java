package com.doctorclinicapp.backend.controller.assessment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doctorclinicapp.backend.model.assessment.LaboratoryMaster;
import com.doctorclinicapp.backend.repository.assessment.LaboratoryMasterRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/laboratory-master")
@RequiredArgsConstructor
public class LaboratoryMasterController {
	
// Injected dependency of repository in controller to fetch or send client request to the application
    private final LaboratoryMasterRepository repository;

    @GetMapping
    public Page<LaboratoryMaster> getLaboratoryTests(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return repository.findByTestNameContainingIgnoreCase(search, pageable);
    }
}