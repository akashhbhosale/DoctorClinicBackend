package com.doctorclinicapp.backend.service.assessment.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.doctorclinicapp.backend.dto.assessment.AddEncounterLaboratoryRequest;
import com.doctorclinicapp.backend.dto.assessment.EncounterLaboratoryFileResponse;
import com.doctorclinicapp.backend.dto.assessment.EncounterLaboratoryResponse;
import com.doctorclinicapp.backend.exception.ResourceNotFoundException;
import com.doctorclinicapp.backend.model.assessment.EncounterLaboratory;
import com.doctorclinicapp.backend.model.assessment.EncounterLaboratoryFile;
import com.doctorclinicapp.backend.model.assessment.LaboratoryMaster;
import com.doctorclinicapp.backend.model.encounter.Encounter;
import com.doctorclinicapp.backend.repository.assessment.EncounterLaboratoryFileRepository;
import com.doctorclinicapp.backend.repository.assessment.EncounterLaboratoryRepository;
import com.doctorclinicapp.backend.repository.assessment.LaboratoryMasterRepository;
import com.doctorclinicapp.backend.repository.encounter.EncounterRepository;
import com.doctorclinicapp.backend.service.assessment.EncounterLaboratoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncounterLaboratoryServiceImpl implements EncounterLaboratoryService {

    private final EncounterRepository encounterRepository;
    private final EncounterLaboratoryRepository encounterLaboratoryRepository;
    private final EncounterLaboratoryFileRepository encounterLaboratoryFileRepository;
    private final LaboratoryMasterRepository laboratoryMasterRepository;

    private final String uploadDir =
            "D:/Ofiice Sushodh Work/DoctorClinicApp/com.doctorclinicapp.backend/uploads/laboratory";

    @Override
    public EncounterLaboratoryResponse addLaboratory(AddEncounterLaboratoryRequest request) {
        Encounter encounter = encounterRepository.findById(request.getEncounterId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Encounter not found with id: " + request.getEncounterId()));

        LaboratoryMaster laboratoryMaster = laboratoryMasterRepository.findById(request.getLaboratoryMasterId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Laboratory master not found with id: " + request.getLaboratoryMasterId()));

        EncounterLaboratory laboratory = EncounterLaboratory.builder()
                .encounter(encounter)
                .laboratoryMaster(laboratoryMaster)
                .labResult(request.getLabResult())
                .build();

        EncounterLaboratory savedLaboratory = encounterLaboratoryRepository.save(laboratory);
        return mapToResponse(savedLaboratory);
    }

    @Override
    public List<EncounterLaboratoryResponse> getLaboratoryByEncounterId(Long encounterId) {
        encounterRepository.findById(encounterId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Encounter not found with id: " + encounterId));

        return encounterLaboratoryRepository.findByEncounterIdOrderByCreatedAtDesc(encounterId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteLaboratory(Long laboratoryId) {
        EncounterLaboratory laboratory = encounterLaboratoryRepository.findById(laboratoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Laboratory record not found with id: " + laboratoryId));

        List<EncounterLaboratoryFile> files = encounterLaboratoryFileRepository
                .findByLaboratoryIdOrderByUploadedAtDesc(laboratoryId);

        for (EncounterLaboratoryFile file : files) {
            deletePhysicalFileIfExists(file.getFilePath());
        }

        encounterLaboratoryRepository.delete(laboratory);
    }

    @Override
    public EncounterLaboratoryFileResponse uploadLaboratoryFile(Long laboratoryId, MultipartFile file) {
        EncounterLaboratory laboratory = encounterLaboratoryRepository.findById(laboratoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Laboratory record not found with id: " + laboratoryId));

        validatePdfFile(file);

        try {
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);

            String originalFileName = file.getOriginalFilename() != null
                    ? file.getOriginalFilename()
                    : "lab-report.pdf";

            String storedFileName = UUID.randomUUID() + "_" + originalFileName.replaceAll("\\s+", "_");
            Path filePath = uploadPath.resolve(storedFileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            EncounterLaboratoryFile laboratoryFile = EncounterLaboratoryFile.builder()
                    .laboratory(laboratory)
                    .originalFileName(originalFileName)
                    .storedFileName(storedFileName)
                    .filePath(filePath.toString())
                    .contentType(file.getContentType())
                    .fileSize(file.getSize())
                    .build();

            EncounterLaboratoryFile savedFile = encounterLaboratoryFileRepository.save(laboratoryFile);
            return mapFileToResponse(savedFile);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload laboratory PDF file");
        }
    }

    @Override
    public List<EncounterLaboratoryFileResponse> getFilesByLaboratoryId(Long laboratoryId) {
        encounterLaboratoryRepository.findById(laboratoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Laboratory record not found with id: " + laboratoryId));

        return encounterLaboratoryFileRepository.findByLaboratoryIdOrderByUploadedAtDesc(laboratoryId)
                .stream()
                .map(this::mapFileToResponse)
                .toList();
    }

    @Override
    public void deleteLaboratoryFile(Long fileId) {
        EncounterLaboratoryFile laboratoryFile = encounterLaboratoryFileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Laboratory file not found with id: " + fileId));

        deletePhysicalFileIfExists(laboratoryFile.getFilePath());
        encounterLaboratoryFileRepository.delete(laboratoryFile);
    }

    private void validatePdfFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("PDF file is required");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.equalsIgnoreCase("application/pdf")) {
            throw new IllegalArgumentException("Only PDF files are allowed");
        }
    }

    private void deletePhysicalFileIfExists(String filePath) {
        if (filePath == null || filePath.isBlank()) return;

        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete laboratory file from server");
        }
    }

    private EncounterLaboratoryResponse mapToResponse(EncounterLaboratory laboratory) {
        List<EncounterLaboratoryFileResponse> fileResponses =
                laboratory.getFiles() == null
                        ? List.of()
                        : laboratory.getFiles().stream()
                                .map(this::mapFileToResponse)
                                .toList();

        return EncounterLaboratoryResponse.builder()
                .id(laboratory.getId())
                .encounterId(laboratory.getEncounter().getId())
                .laboratoryMasterId(laboratory.getLaboratoryMaster().getId())
                .testName(laboratory.getLaboratoryMaster().getTestName())
                .labResult(laboratory.getLabResult())
                .createdAt(laboratory.getCreatedAt())
                .updatedAt(laboratory.getUpdatedAt())
                .files(fileResponses)
                .build();
    }

    private EncounterLaboratoryFileResponse mapFileToResponse(EncounterLaboratoryFile file) {
        return EncounterLaboratoryFileResponse.builder()
                .id(file.getId())
                .originalFileName(file.getOriginalFileName())
                .storedFileName(file.getStoredFileName())
                .filePath(file.getFilePath())
                .contentType(file.getContentType())
                .fileSize(file.getFileSize())
                .uploadedAt(file.getUploadedAt())
                .build();
    }
}