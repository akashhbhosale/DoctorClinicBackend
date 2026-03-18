package com.doctorclinicapp.backend.service.history;

import java.util.List;

import com.doctorclinicapp.backend.dto.history.PastMedicalHistoryDTO;
import com.doctorclinicapp.backend.dto.history.FamilyHistoryDTO;
import com.doctorclinicapp.backend.dto.history.HistoryMasterDataDTO;
import com.doctorclinicapp.backend.dto.history.HistoryResponseDTO;

public interface HistoryService {

    List<PastMedicalHistoryDTO> getPastMedicalHistory(Long patientId);

    PastMedicalHistoryDTO addPastMedicalHistory(PastMedicalHistoryDTO dto);

    void deletePastMedicalHistory(Long id);


    List<FamilyHistoryDTO> getFamilyHistory(Long patientId);

    FamilyHistoryDTO addFamilyHistory(FamilyHistoryDTO dto);

    void deleteFamilyHistory(Long id);
    
    HistoryResponseDTO getPatientHistory(Long patientId);

    HistoryMasterDataDTO getHistoryMasterData();
}