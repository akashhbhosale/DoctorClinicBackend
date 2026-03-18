package com.doctorclinicapp.backend.controller.encounter;


import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doctorclinicapp.backend.dto.encounter.AddEncounterChiefComplaintRequest;
import com.doctorclinicapp.backend.dto.encounter.EncounterChiefComplaintResponse;
import com.doctorclinicapp.backend.model.encounter.EncounterChiefComplaint;
import com.doctorclinicapp.backend.service.encounter.EncounterChiefComplaintService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/encounter-complaints")
@RequiredArgsConstructor
public class EncounterChiefComplaintController {

    private final EncounterChiefComplaintService service;

    @PostMapping
    public EncounterChiefComplaint addComplaint(
            @RequestBody @Valid AddEncounterChiefComplaintRequest request) {

        return service.addComplaint(request);
    }
    
    // Get
    @GetMapping("/{encounterId}")
    public List<EncounterChiefComplaintResponse> getComplaints(
            @PathVariable Long encounterId) {

        return service.getComplaintsByEncounterId(encounterId);
    }
    
    //delete
   @DeleteMapping("/{id}")
    public void deleteComplaint(@PathVariable Long id) {
        service.deleteComplaint(id);
    }
}