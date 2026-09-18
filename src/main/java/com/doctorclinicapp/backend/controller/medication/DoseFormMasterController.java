package com.doctorclinicapp.backend.controller.medication;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.medication.DoseFormMasterDTO;
import com.doctorclinicapp.backend.model.medication.DoseFormMaster;
import com.doctorclinicapp.backend.repository.medication.DoseFormMasterRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dose-form-master")
@RequiredArgsConstructor
public class DoseFormMasterController {

    private final DoseFormMasterRepository repository;

    @GetMapping
    public PageResponse<DoseFormMasterDTO> getAll(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<DoseFormMaster> resultPage = search.isBlank()
                ? repository.findAll(pageable)
                : repository.findByDoseFormNameContainingIgnoreCase(search, pageable);

        List<DoseFormMasterDTO> content = resultPage.getContent().stream()
                .map(m -> DoseFormMasterDTO.builder()
                        .id(m.getId())
                        .doseFormName(m.getDoseFormName())
                        .build())
                .collect(Collectors.toList());

        return PageResponse.<DoseFormMasterDTO>builder()
                .content(content)
                .page(resultPage.getNumber())
                .size(resultPage.getSize())
                .totalElements(resultPage.getTotalElements())
                .totalPages(resultPage.getTotalPages())
                .last(resultPage.isLast())
                .build();
    }
}
