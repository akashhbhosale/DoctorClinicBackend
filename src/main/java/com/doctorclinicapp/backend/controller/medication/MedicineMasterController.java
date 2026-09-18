package com.doctorclinicapp.backend.controller.medication;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.medication.MedicineMasterDTO;
import com.doctorclinicapp.backend.enums.MedicineType;
import com.doctorclinicapp.backend.model.medication.MedicineMaster;
import com.doctorclinicapp.backend.repository.medication.MedicineMasterRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/medicine-master")
@RequiredArgsConstructor
public class MedicineMasterController {

    private final MedicineMasterRepository repository;

    // type = GENERIC or BRAND — required, drives the Generics/Brand radio toggle.
    @GetMapping
    public PageResponse<MedicineMasterDTO> getMedicines(
            @RequestParam String type,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        MedicineType medicineType = MedicineType.valueOf(type.toUpperCase());
        Pageable pageable = PageRequest.of(page, size);

        Page<MedicineMaster> resultPage = search.isBlank()
                ? repository.findByMedicineType(medicineType, pageable)
                : repository.findByMedicineTypeAndMedicineNameContainingIgnoreCase(
                        medicineType, search, pageable);

        List<MedicineMasterDTO> content = resultPage.getContent().stream()
                .map(m -> MedicineMasterDTO.builder()
                        .id(m.getId())
                        .medicineName(m.getMedicineName())
                        .medicineType(m.getMedicineType().name())
                        .build())
                .collect(Collectors.toList());

        return PageResponse.<MedicineMasterDTO>builder()
                .content(content)
                .page(resultPage.getNumber())
                .size(resultPage.getSize())
                .totalElements(resultPage.getTotalElements())
                .totalPages(resultPage.getTotalPages())
                .last(resultPage.isLast())
                .build();
    }
}
