package com.doctorclinicapp.backend.service.encounter;

import java.util.List;

import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.encounter.AddEncounterDiagnosisRequest;
import com.doctorclinicapp.backend.dto.encounter.EncounterDiagnosisResponse;
import com.doctorclinicapp.backend.exception.ResourceNotFoundException;
import com.doctorclinicapp.backend.model.encounter.Encounter;
import com.doctorclinicapp.backend.model.encounter.EncounterDiagnosis;
import com.doctorclinicapp.backend.model.encounter.ICDCode;
import com.doctorclinicapp.backend.repository.encounter.EncounterDiagnosisRepository;
import com.doctorclinicapp.backend.repository.encounter.EncounterRepository;
import com.doctorclinicapp.backend.repository.encounter.ICDCodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncounterDiagnosisService {

    private final EncounterDiagnosisRepository diagnosisRepository;
    private final EncounterRepository encounterRepository;
    private final ICDCodeRepository icdCodeRepository;

    public EncounterDiagnosis addDiagnosis(AddEncounterDiagnosisRequest request) {

        // 1️⃣ Find Encounter
        Encounter encounter = encounterRepository.findById(request.getEncounterId())
                .orElseThrow(() -> new ResourceNotFoundException("Encounter not found"));

        // 2️⃣ Find ICD Code
        ICDCode icdCode = icdCodeRepository.findById(request.getIcdCodeId())
                .orElseThrow(() -> new ResourceNotFoundException("ICD code not found"));

        // 🔥 3️⃣ Duplicate Check (ADD HERE)
        boolean alreadyExists =
                diagnosisRepository.existsByEncounterIdAndIcdCodeId(
                        request.getEncounterId(),
                        request.getIcdCodeId());

        if (alreadyExists) {
        	throw new IllegalArgumentException("Diagnosis already exists for this encounter");
        }

        // 4️⃣ Create Diagnosis
        EncounterDiagnosis diagnosis = EncounterDiagnosis.builder()
                .encounter(encounter)
                .icdCode(icdCode)
                .diagnosisType(request.getDiagnosisType())
                .build();

        // 5️⃣ Save
        return diagnosisRepository.save(diagnosis);
    }
    
    // Get Diagnoses
    public List<EncounterDiagnosisResponse> getDiagnosesByEncounterId(Long encounterId) {

        List<EncounterDiagnosis> diagnoses =
                diagnosisRepository.findByEncounter_Id(encounterId);

        return diagnoses.stream()
                .map(d -> new EncounterDiagnosisResponse(
                		d.getId(),
                        d.getDiagnosisType(),   //no .name()
                        d.getIcdCode().getCode(),
                        d.getIcdCode().getDescription()
                ))
                .toList();
    }
    
    // Delete
    public void deleteDiagnosis(Long id) {

        EncounterDiagnosis diagnosis = diagnosisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diagnosis not found"));

        diagnosisRepository.delete(diagnosis);
    }
}