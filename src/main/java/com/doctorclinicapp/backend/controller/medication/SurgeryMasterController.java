package com.doctorclinicapp.backend.controller.medication;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.medication.SurgeryMasterDTO;
import com.doctorclinicapp.backend.model.medication.SurgeryMaster;
import com.doctorclinicapp.backend.repository.medication.SurgeryMasterRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/surgery-master")
@RequiredArgsConstructor
public class SurgeryMasterController {

    private final SurgeryMasterRepository repository;

    @GetMapping
    public PageResponse<SurgeryMasterDTO> getAll(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<SurgeryMaster> resultPage = search.isBlank()
                ? repository.findAll(pageable)
                : repository.findBySurgeryNameContainingIgnoreCase(search, pageable);

        List<SurgeryMasterDTO> content = resultPage.getContent().stream()
                .map(m -> SurgeryMasterDTO.builder()
                        .id(m.getId())
                        .surgeryName(m.getSurgeryName())
                        .build())
                .collect(Collectors.toList());

        return PageResponse.<SurgeryMasterDTO>builder()
                .content(content)
                .page(resultPage.getNumber())
                .size(resultPage.getSize())
                .totalElements(resultPage.getTotalElements())
                .totalPages(resultPage.getTotalPages())
                .last(resultPage.isLast())
                .build();
    }
}
