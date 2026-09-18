package com.doctorclinicapp.backend.controller.medication;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.medication.DirectionOfUseMasterDTO;
import com.doctorclinicapp.backend.model.medication.DirectionOfUseMaster;
import com.doctorclinicapp.backend.repository.medication.DirectionOfUseMasterRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/direction-of-use-master")
@RequiredArgsConstructor
public class DirectionOfUseMasterController {

    private final DirectionOfUseMasterRepository repository;

    @GetMapping
    public PageResponse<DirectionOfUseMasterDTO> getAll(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<DirectionOfUseMaster> resultPage = search.isBlank()
                ? repository.findAll(pageable)
                : repository.findByDirectionNameContainingIgnoreCase(search, pageable);

        List<DirectionOfUseMasterDTO> content = resultPage.getContent().stream()
                .map(m -> DirectionOfUseMasterDTO.builder()
                        .id(m.getId())
                        .directionName(m.getDirectionName())
                        .build())
                .collect(Collectors.toList());

        return PageResponse.<DirectionOfUseMasterDTO>builder()
                .content(content)
                .page(resultPage.getNumber())
                .size(resultPage.getSize())
                .totalElements(resultPage.getTotalElements())
                .totalPages(resultPage.getTotalPages())
                .last(resultPage.isLast())
                .build();
    }
}
