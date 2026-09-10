package com.doctorclinicapp.backend.controller.allergy;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.doctorclinicapp.backend.dto.allergy.AllergyDTO;
import com.doctorclinicapp.backend.dto.allergy.AllergyMasterDataDTO;
import com.doctorclinicapp.backend.service.allergy.AllergyService;

@RestController
@RequestMapping("/api/allergies")
@RequiredArgsConstructor
public class AllergyController {

    private final AllergyService allergyService;

    // Get all allergies for a patient
    @GetMapping("/patient/{patientId}")
    public List<AllergyDTO> getAllergiesByPatient(@PathVariable Long patientId) {
        return allergyService.getAllergiesByPatient(patientId);
    }

    // Add an allergy
    @PostMapping
    public AllergyDTO addAllergy(@RequestBody AllergyDTO dto) {
        return allergyService.addAllergy(dto);
    }

    // Delete an allergy
    @DeleteMapping("/{id}")
    public void deleteAllergy(@PathVariable Long id) {
        allergyService.deleteAllergy(id);
    }

    // Master data for the dropdowns (substances, manifestations, and the
    // fixed criticality/verification-status option lists)
    @GetMapping("/master-data")
    public AllergyMasterDataDTO getAllergyMasterData() {
        return allergyService.getAllergyMasterData();
    }
}
