package com.doctorclinicapp.backend.service.procedure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.procedure.ProcedureMasterResponse;
import com.doctorclinicapp.backend.model.procedure.ProcedureMaster;
import com.doctorclinicapp.backend.repository.procedure.ProcedureMasterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcedureMasterService {

    private final ProcedureMasterRepository procedureRepo;

    public PageResponse<ProcedureMasterResponse> getProcedures(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProcedureMaster> procedurePage = procedureRepo.findAll(pageable);

        List<ProcedureMasterResponse> content = procedurePage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PageResponse.<ProcedureMasterResponse>builder()
                .content(content)
                .page(procedurePage.getNumber())
                .size(procedurePage.getSize())
                .totalElements(procedurePage.getTotalElements())
                .totalPages(procedurePage.getTotalPages())
                .last(procedurePage.isLast())
                .build();
    }

    public PageResponse<ProcedureMasterResponse> searchProcedures(String keyword, int page, int size) {

        String searchKeyword = keyword == null ? "" : keyword.trim();
        page = Math.max(page, 0);
        size = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        Page<ProcedureMaster> procedurePage;

        if (searchKeyword.isBlank()) {
            procedurePage = procedureRepo.findAll(pageable);
        } else {
            procedurePage = procedureRepo.findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(
                    searchKeyword, searchKeyword, pageable);
        }

        List<ProcedureMasterResponse> content = procedurePage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PageResponse.<ProcedureMasterResponse>builder()
                .content(content)
                .page(procedurePage.getNumber())
                .size(procedurePage.getSize())
                .totalElements(procedurePage.getTotalElements())
                .totalPages(procedurePage.getTotalPages())
                .last(procedurePage.isLast())
                .build();
    }

    private ProcedureMasterResponse mapToResponse(ProcedureMaster procedure) {
        return ProcedureMasterResponse.builder()
                .id(procedure.getId())
                .code(procedure.getCode())
                .name(procedure.getName())
                .build();
    }
}