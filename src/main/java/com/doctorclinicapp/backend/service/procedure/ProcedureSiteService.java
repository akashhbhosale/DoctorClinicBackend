package com.doctorclinicapp.backend.service.procedure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.procedure.ProcedureSiteResponse;
import com.doctorclinicapp.backend.model.procedure.ProcedureSiteMaster;
import com.doctorclinicapp.backend.repository.procedure.ProcedureSiteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcedureSiteService {

    private final ProcedureSiteRepository siteRepo;

    public PageResponse<ProcedureSiteResponse> getSites(int page, int size) {
        page = Math.max(page, 0);
        size = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<ProcedureSiteMaster> sitePage = siteRepo.findAll(pageable);

        List<ProcedureSiteResponse> content = sitePage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PageResponse.<ProcedureSiteResponse>builder()
                .content(content)
                .page(sitePage.getNumber())
                .size(sitePage.getSize())
                .totalElements(sitePage.getTotalElements())
                .totalPages(sitePage.getTotalPages())
                .last(sitePage.isLast())
                .build();
    }

    public PageResponse<ProcedureSiteResponse> searchSites(String keyword, int page, int size) {
        String searchKeyword = keyword == null ? "" : keyword.trim();
        page = Math.max(page, 0);
        size = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        Page<ProcedureSiteMaster> sitePage;
        if (searchKeyword.isBlank()) {
            sitePage = siteRepo.findAll(pageable);
        } else {
            sitePage = siteRepo.findByNameContainingIgnoreCase(searchKeyword, pageable);
        }

        List<ProcedureSiteResponse> content = sitePage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PageResponse.<ProcedureSiteResponse>builder()
                .content(content)
                .page(sitePage.getNumber())
                .size(sitePage.getSize())
                .totalElements(sitePage.getTotalElements())
                .totalPages(sitePage.getTotalPages())
                .last(sitePage.isLast())
                .build();
    }

    private ProcedureSiteResponse mapToResponse(ProcedureSiteMaster site) {
        return ProcedureSiteResponse.builder()
                .id(site.getId())
                .name(site.getName())
                .build();
    }
}