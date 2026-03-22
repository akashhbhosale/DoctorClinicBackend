package com.doctorclinicapp.backend.service.procedure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.procedure.ProcedureMethodResponse;
import com.doctorclinicapp.backend.model.procedure.ProcedureMethodMaster;
import com.doctorclinicapp.backend.repository.procedure.ProcedureMethodRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcedureMethodService {

    private final ProcedureMethodRepository methodRepo;

    public PageResponse<ProcedureMethodResponse> getMethods(int page, int size) {
        page = Math.max(page, 0);
        size = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<ProcedureMethodMaster> methodPage = methodRepo.findAll(pageable);

        List<ProcedureMethodResponse> content = methodPage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PageResponse.<ProcedureMethodResponse>builder()
                .content(content)
                .page(methodPage.getNumber())
                .size(methodPage.getSize())
                .totalElements(methodPage.getTotalElements())
                .totalPages(methodPage.getTotalPages())
                .last(methodPage.isLast())
                .build();
    }

    public PageResponse<ProcedureMethodResponse> searchMethods(String keyword, int page, int size) {
        String searchKeyword = keyword == null ? "" : keyword.trim();
        page = Math.max(page, 0);
        size = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        Page<ProcedureMethodMaster> methodPage;
        if (searchKeyword.isBlank()) {
            methodPage = methodRepo.findAll(pageable);
        } else {
            methodPage = methodRepo.findByNameContainingIgnoreCase(searchKeyword, pageable);
        }

        List<ProcedureMethodResponse> content = methodPage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PageResponse.<ProcedureMethodResponse>builder()
                .content(content)
                .page(methodPage.getNumber())
                .size(methodPage.getSize())
                .totalElements(methodPage.getTotalElements())
                .totalPages(methodPage.getTotalPages())
                .last(methodPage.isLast())
                .build();
    }

    private ProcedureMethodResponse mapToResponse(ProcedureMethodMaster method) {
        return ProcedureMethodResponse.builder()
                .id(method.getId())
                .name(method.getName())
                .build();
    }
}