package com.doctorclinicapp.backend.service.assessment;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.doctorclinicapp.backend.dto.assessment.AddEncounterRadiologyRequest;
import com.doctorclinicapp.backend.dto.assessment.EncounterRadiologyFileResponse;
import com.doctorclinicapp.backend.dto.assessment.EncounterRadiologyResponse;
import com.doctorclinicapp.backend.dto.assessment.FileContentResponse;

public interface EncounterRadiologyService {

    EncounterRadiologyResponse addRadiology(AddEncounterRadiologyRequest request);

    List<EncounterRadiologyResponse> getRadiologyByEncounterId(Long encounterId);

    void deleteRadiology(Long radiologyId);

    EncounterRadiologyFileResponse uploadRadiologyFile(Long radiologyId, MultipartFile file);

    List<EncounterRadiologyFileResponse> getFilesByRadiologyId(Long radiologyId);

    void deleteRadiologyFile(Long fileId);

    FileContentResponse downloadRadiologyFile(Long fileId);
}
