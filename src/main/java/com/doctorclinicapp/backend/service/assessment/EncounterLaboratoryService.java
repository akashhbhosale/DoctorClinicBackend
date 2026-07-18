package com.doctorclinicapp.backend.service.assessment;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.doctorclinicapp.backend.dto.assessment.AddEncounterLaboratoryRequest;
import com.doctorclinicapp.backend.dto.assessment.EncounterLaboratoryFileResponse;
import com.doctorclinicapp.backend.dto.assessment.EncounterLaboratoryResponse;

public interface EncounterLaboratoryService {

    EncounterLaboratoryResponse addLaboratory(AddEncounterLaboratoryRequest request);

    List<EncounterLaboratoryResponse> getLaboratoryByEncounterId(Long encounterId);

    void deleteLaboratory(Long laboratoryId);

    EncounterLaboratoryFileResponse uploadLaboratoryFile(Long laboratoryId, MultipartFile file);

    List<EncounterLaboratoryFileResponse> getFilesByLaboratoryId(Long laboratoryId);

    void deleteLaboratoryFile(Long fileId);
}