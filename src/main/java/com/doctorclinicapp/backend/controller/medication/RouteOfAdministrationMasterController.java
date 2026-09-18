package com.doctorclinicapp.backend.controller.medication;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.medication.RouteOfAdministrationMasterDTO;
import com.doctorclinicapp.backend.model.medication.RouteOfAdministrationMaster;
import com.doctorclinicapp.backend.repository.medication.RouteOfAdministrationMasterRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/route-of-administration-master")
@RequiredArgsConstructor
public class RouteOfAdministrationMasterController {

    private final RouteOfAdministrationMasterRepository repository;

    @GetMapping
    public PageResponse<RouteOfAdministrationMasterDTO> getAll(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<RouteOfAdministrationMaster> resultPage = search.isBlank()
                ? repository.findAll(pageable)
                : repository.findByRouteNameContainingIgnoreCase(search, pageable);

        List<RouteOfAdministrationMasterDTO> content = resultPage.getContent().stream()
                .map(m -> RouteOfAdministrationMasterDTO.builder()
                        .id(m.getId())
                        .routeName(m.getRouteName())
                        .build())
                .collect(Collectors.toList());

        return PageResponse.<RouteOfAdministrationMasterDTO>builder()
                .content(content)
                .page(resultPage.getNumber())
                .size(resultPage.getSize())
                .totalElements(resultPage.getTotalElements())
                .totalPages(resultPage.getTotalPages())
                .last(resultPage.isLast())
                .build();
    }
}
