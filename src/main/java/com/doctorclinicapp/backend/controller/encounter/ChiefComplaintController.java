package com.doctorclinicapp.backend.controller.encounter;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;
import com.doctorclinicapp.backend.repository.encounter.ChiefComplaintRepository;
import com.doctorclinicapp.backend.model.encounter.ChiefComplaint;

@RestController
@RequestMapping("/api/chief-complaints")
@RequiredArgsConstructor
public class ChiefComplaintController {

    private final ChiefComplaintRepository repository;

    @GetMapping
    public Page<ChiefComplaint> searchComplaints(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return repository.findByComplaintNameContainingIgnoreCase(search, pageable);
    }
}