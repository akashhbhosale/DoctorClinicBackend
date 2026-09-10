package com.doctorclinicapp.backend.service.allergy;

import java.util.List;

import com.doctorclinicapp.backend.dto.allergy.AllergyDTO;
import com.doctorclinicapp.backend.dto.allergy.AllergyMasterDataDTO;

public interface AllergyService {

    List<AllergyDTO> getAllergiesByPatient(Long patientId);

    AllergyDTO addAllergy(AllergyDTO dto);

    void deleteAllergy(Long id);

    AllergyMasterDataDTO getAllergyMasterData();
}
