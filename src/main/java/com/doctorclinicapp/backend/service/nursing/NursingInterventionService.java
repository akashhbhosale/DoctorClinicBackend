package com.doctorclinicapp.backend.service.nursing;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.nursing.NursingInterventionResponse;
import com.doctorclinicapp.backend.model.nursing.NursingInterventionMaster;
import com.doctorclinicapp.backend.repository.nursing.NursingInterventionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NursingInterventionService {

    private final NursingInterventionRepository repo;

    public PageResponse<NursingInterventionResponse> getAll(int page, int size) {
        page = Math.max(page, 0);
        size = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<NursingInterventionMaster> resultPage = repo.findAll(pageable);

        List<NursingInterventionResponse> content = resultPage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PageResponse.<NursingInterventionResponse>builder()
                .content(content)
                .page(resultPage.getNumber())
                .size(resultPage.getSize())
                .totalElements(resultPage.getTotalElements())
                .totalPages(resultPage.getTotalPages())
                .last(resultPage.isLast())
                .build();
    }

    public PageResponse<NursingInterventionResponse> search(String keyword, int page, int size) {
        String searchKeyword = keyword == null ? "" : keyword.trim();
        page = Math.max(page, 0);
        size = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        Page<NursingInterventionMaster> resultPage;
        if (searchKeyword.isBlank()) {
            resultPage = repo.findAll(pageable);
        } else {
            resultPage = repo.findByNameContainingIgnoreCase(searchKeyword, pageable);
        }

        List<NursingInterventionResponse> content = resultPage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PageResponse.<NursingInterventionResponse>builder()
                .content(content)
                .page(resultPage.getNumber())
                .size(resultPage.getSize())
                .totalElements(resultPage.getTotalElements())
                .totalPages(resultPage.getTotalPages())
                .last(resultPage.isLast())
                .build();
    }

    private NursingInterventionResponse mapToResponse(NursingInterventionMaster entity) {
        return NursingInterventionResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
