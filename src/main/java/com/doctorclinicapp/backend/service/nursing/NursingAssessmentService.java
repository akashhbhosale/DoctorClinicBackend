package com.doctorclinicapp.backend.service.nursing;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.nursing.NursingAssessmentResponse;
import com.doctorclinicapp.backend.model.nursing.NursingAssessmentMaster;
import com.doctorclinicapp.backend.repository.nursing.NursingAssessmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NursingAssessmentService {

    private final NursingAssessmentRepository repo;

    public PageResponse<NursingAssessmentResponse> getAll(int page, int size) {
        page = Math.max(page, 0);
        size = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<NursingAssessmentMaster> resultPage = repo.findAll(pageable);

        List<NursingAssessmentResponse> content = resultPage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PageResponse.<NursingAssessmentResponse>builder()
                .content(content)
                .page(resultPage.getNumber())
                .size(resultPage.getSize())
                .totalElements(resultPage.getTotalElements())
                .totalPages(resultPage.getTotalPages())
                .last(resultPage.isLast())
                .build();
    }

    public PageResponse<NursingAssessmentResponse> search(String keyword, int page, int size) {
        String searchKeyword = keyword == null ? "" : keyword.trim();
        page = Math.max(page, 0);
        size = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        Page<NursingAssessmentMaster> resultPage;
        if (searchKeyword.isBlank()) {
            resultPage = repo.findAll(pageable);
        } else {
            resultPage = repo.findByNameContainingIgnoreCase(searchKeyword, pageable);
        }

        List<NursingAssessmentResponse> content = resultPage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PageResponse.<NursingAssessmentResponse>builder()
                .content(content)
                .page(resultPage.getNumber())
                .size(resultPage.getSize())
                .totalElements(resultPage.getTotalElements())
                .totalPages(resultPage.getTotalPages())
                .last(resultPage.isLast())
                .build();
    }

    private NursingAssessmentResponse mapToResponse(NursingAssessmentMaster entity) {
        return NursingAssessmentResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
