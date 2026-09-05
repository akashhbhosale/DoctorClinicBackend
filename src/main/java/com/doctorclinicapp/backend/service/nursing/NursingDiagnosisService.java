package com.doctorclinicapp.backend.service.nursing;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.nursing.NursingDiagnosisResponse;
import com.doctorclinicapp.backend.model.nursing.NursingDiagnosisMaster;
import com.doctorclinicapp.backend.repository.nursing.NursingDiagnosisRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NursingDiagnosisService {

    private final NursingDiagnosisRepository repo;

    public PageResponse<NursingDiagnosisResponse> getAll(int page, int size) {
        page = Math.max(page, 0);
        size = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<NursingDiagnosisMaster> resultPage = repo.findAll(pageable);

        List<NursingDiagnosisResponse> content = resultPage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PageResponse.<NursingDiagnosisResponse>builder()
                .content(content)
                .page(resultPage.getNumber())
                .size(resultPage.getSize())
                .totalElements(resultPage.getTotalElements())
                .totalPages(resultPage.getTotalPages())
                .last(resultPage.isLast())
                .build();
    }

    public PageResponse<NursingDiagnosisResponse> search(String keyword, int page, int size) {
        String searchKeyword = keyword == null ? "" : keyword.trim();
        page = Math.max(page, 0);
        size = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        Page<NursingDiagnosisMaster> resultPage;
        if (searchKeyword.isBlank()) {
            resultPage = repo.findAll(pageable);
        } else {
            resultPage = repo.findByNameContainingIgnoreCase(searchKeyword, pageable);
        }

        List<NursingDiagnosisResponse> content = resultPage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PageResponse.<NursingDiagnosisResponse>builder()
                .content(content)
                .page(resultPage.getNumber())
                .size(resultPage.getSize())
                .totalElements(resultPage.getTotalElements())
                .totalPages(resultPage.getTotalPages())
                .last(resultPage.isLast())
                .build();
    }

    private NursingDiagnosisResponse mapToResponse(NursingDiagnosisMaster entity) {
        return NursingDiagnosisResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
