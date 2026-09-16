package com.doctorclinicapp.backend.service.assessment.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.doctorclinicapp.backend.dto.assessment.AddEncounterRadiologyRequest;
import com.doctorclinicapp.backend.dto.assessment.EncounterRadiologyFileResponse;
import com.doctorclinicapp.backend.dto.assessment.EncounterRadiologyResponse;
import com.doctorclinicapp.backend.dto.assessment.FileContentResponse;
import com.doctorclinicapp.backend.exception.ResourceNotFoundException;
import com.doctorclinicapp.backend.model.assessment.EncounterRadiology;
import com.doctorclinicapp.backend.model.assessment.EncounterRadiologyFile;
import com.doctorclinicapp.backend.model.assessment.RadiologyMaster;
import com.doctorclinicapp.backend.model.encounter.Encounter;
import com.doctorclinicapp.backend.repository.assessment.EncounterRadiologyFileRepository;
import com.doctorclinicapp.backend.repository.assessment.EncounterRadiologyRepository;
import com.doctorclinicapp.backend.repository.assessment.RadiologyMasterRepository;
import com.doctorclinicapp.backend.repository.encounter.EncounterRepository;
import com.doctorclinicapp.backend.service.assessment.EncounterRadiologyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncounterRadiologyServiceImpl implements EncounterRadiologyService {

    private final EncounterRepository encounterRepository;
    private final EncounterRadiologyRepository encounterRadiologyRepository;
    private final EncounterRadiologyFileRepository encounterRadiologyFileRepository;
    private final RadiologyMasterRepository radiologyMasterRepository;

    // Radiology reports commonly come back as scanned images (X-ray/CT/MRI
    // films) as well as PDFs, unlike lab reports which are almost always
    // PDF — so this accepts both, where Laboratory's upload stays PDF-only.
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "application/pdf", "image/jpeg", "image/png", "image/jpg"
    );

    // Same folder convention as Laboratory's uploadDir. NOTE: like the
    // existing Laboratory upload path, this is a local absolute path —
    // fine for local dev, but should move to an environment-configurable
    // path (or cloud storage) before any real deployment. Flagging this
    // as a known limitation rather than fixing it here, since fixing it
    // means also fixing Laboratory's identical existing issue, which is a
    // separate piece of work.
    private final String uploadDir =
            "D:/Ofiice Sushodh Work/DoctorClinicApp/com.doctorclinicapp.backend/uploads/radiology";

    @Override
    public EncounterRadiologyResponse addRadiology(AddEncounterRadiologyRequest request) {
        Encounter encounter = encounterRepository.findById(request.getEncounterId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Encounter not found with id: " + request.getEncounterId()));

        RadiologyMaster radiologyMaster = radiologyMasterRepository.findById(request.getRadiologyMasterId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Radiology master not found with id: " + request.getRadiologyMasterId()));

        EncounterRadiology radiology = EncounterRadiology.builder()
                .encounter(encounter)
                .radiologyMaster(radiologyMaster)
                .radiologyResult(request.getRadiologyResult())
                .build();

        EncounterRadiology saved = encounterRadiologyRepository.save(radiology);
        return mapToResponse(saved);
    }

    @Override
    public List<EncounterRadiologyResponse> getRadiologyByEncounterId(Long encounterId) {
        encounterRepository.findById(encounterId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Encounter not found with id: " + encounterId));

        return encounterRadiologyRepository.findByEncounterIdOrderByCreatedAtDesc(encounterId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteRadiology(Long radiologyId) {
        EncounterRadiology radiology = encounterRadiologyRepository.findById(radiologyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Radiology record not found with id: " + radiologyId));

        List<EncounterRadiologyFile> files = encounterRadiologyFileRepository
                .findByRadiologyIdOrderByUploadedAtDesc(radiologyId);

        for (EncounterRadiologyFile file : files) {
            deletePhysicalFileIfExists(file.getFilePath());
        }

        encounterRadiologyRepository.delete(radiology);
    }

    @Override
    public EncounterRadiologyFileResponse uploadRadiologyFile(Long radiologyId, MultipartFile file) {
        EncounterRadiology radiology = encounterRadiologyRepository.findById(radiologyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Radiology record not found with id: " + radiologyId));

        validateFile(file);

        try {
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);

            String originalFileName = file.getOriginalFilename() != null
                    ? file.getOriginalFilename()
                    : "radiology-report";

            String storedFileName = UUID.randomUUID() + "_" + originalFileName.replaceAll("\\s+", "_");
            Path filePath = uploadPath.resolve(storedFileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            EncounterRadiologyFile radiologyFile = EncounterRadiologyFile.builder()
                    .radiology(radiology)
                    .originalFileName(originalFileName)
                    .storedFileName(storedFileName)
                    .filePath(filePath.toString())
                    .contentType(file.getContentType())
                    .fileSize(file.getSize())
                    .build();

            EncounterRadiologyFile savedFile = encounterRadiologyFileRepository.save(radiologyFile);
            return mapFileToResponse(savedFile);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload radiology report file");
        }
    }

    @Override
    public List<EncounterRadiologyFileResponse> getFilesByRadiologyId(Long radiologyId) {
        encounterRadiologyRepository.findById(radiologyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Radiology record not found with id: " + radiologyId));

        return encounterRadiologyFileRepository.findByRadiologyIdOrderByUploadedAtDesc(radiologyId)
                .stream()
                .map(this::mapFileToResponse)
                .toList();
    }

    @Override
    public void deleteRadiologyFile(Long fileId) {
        EncounterRadiologyFile radiologyFile = encounterRadiologyFileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Radiology file not found with id: " + fileId));

        deletePhysicalFileIfExists(radiologyFile.getFilePath());
        encounterRadiologyFileRepository.delete(radiologyFile);
    }

    @Override
    public FileContentResponse downloadRadiologyFile(Long fileId) {
        EncounterRadiologyFile radiologyFile = encounterRadiologyFileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Radiology file not found with id: " + fileId));

        try {
            byte[] data = Files.readAllBytes(Paths.get(radiologyFile.getFilePath()));

            return FileContentResponse.builder()
                    .data(data)
                    .contentType(radiologyFile.getContentType())
                    .fileName(radiologyFile.getOriginalFileName())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read radiology file from server");
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("A report file is required");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            throw new IllegalArgumentException("Only PDF, JPG, or PNG files are allowed");
        }
    }

    private void deletePhysicalFileIfExists(String filePath) {
        if (filePath == null || filePath.isBlank()) return;

        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete radiology file from server");
        }
    }

    private EncounterRadiologyResponse mapToResponse(EncounterRadiology radiology) {
        List<EncounterRadiologyFileResponse> fileResponses =
                radiology.getFiles() == null
                        ? List.of()
                        : radiology.getFiles().stream()
                                .map(this::mapFileToResponse)
                                .toList();

        return EncounterRadiologyResponse.builder()
                .id(radiology.getId())
                .encounterId(radiology.getEncounter().getId())
                .radiologyMasterId(radiology.getRadiologyMaster().getId())
                .orderName(radiology.getRadiologyMaster().getOrderName())
                .radiologyResult(radiology.getRadiologyResult())
                .createdAt(radiology.getCreatedAt())
                .updatedAt(radiology.getUpdatedAt())
                .files(fileResponses)
                .build();
    }

    private EncounterRadiologyFileResponse mapFileToResponse(EncounterRadiologyFile file) {
        return EncounterRadiologyFileResponse.builder()
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
