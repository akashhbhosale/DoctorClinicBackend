package com.doctorclinicapp.backend.service.procedure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.common.PageResponse;
import com.doctorclinicapp.backend.dto.procedure.ProcedureDeviceResponse;
import com.doctorclinicapp.backend.model.procedure.ProcedureDeviceMaster;
import com.doctorclinicapp.backend.repository.procedure.ProcedureDeviceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcedureDeviceService {

    private final ProcedureDeviceRepository deviceRepo;

    // 🔥 GET ALL (PAGINATION)
    public PageResponse<ProcedureDeviceResponse> getDevices(int page, int size) {

        page = Math.max(page, 0);
        size = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        Page<ProcedureDeviceMaster> devicePage = deviceRepo.findAll(pageable);

        List<ProcedureDeviceResponse> content = devicePage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PageResponse.<ProcedureDeviceResponse>builder()
                .content(content)
                .page(devicePage.getNumber())
                .size(devicePage.getSize())
                .totalElements(devicePage.getTotalElements())
                .totalPages(devicePage.getTotalPages())
                .last(devicePage.isLast())
                .build();
    }

    // 🔥 SEARCH
    public PageResponse<ProcedureDeviceResponse> searchDevices(String keyword, int page, int size) {

        String searchKeyword = keyword == null ? "" : keyword.trim();

        page = Math.max(page, 0);
        size = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        Page<ProcedureDeviceMaster> devicePage;

        if (searchKeyword.isBlank()) {
            devicePage = deviceRepo.findAll(pageable);
        } else {
            devicePage = deviceRepo.findByNameContainingIgnoreCase(searchKeyword, pageable);
        }

        List<ProcedureDeviceResponse> content = devicePage.getContent()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return PageResponse.<ProcedureDeviceResponse>builder()
                .content(content)
                .page(devicePage.getNumber())
                .size(devicePage.getSize())
                .totalElements(devicePage.getTotalElements())
                .totalPages(devicePage.getTotalPages())
                .last(devicePage.isLast())
                .build();
    }

    // 🔁 MAPPER
    private ProcedureDeviceResponse mapToResponse(ProcedureDeviceMaster device) {
        return ProcedureDeviceResponse.builder()
                .id(device.getId())
                .name(device.getName())
                .build();
    }
}