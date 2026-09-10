package com.doctorclinicapp.backend.service.allergy.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.allergy.AllergyDTO;
import com.doctorclinicapp.backend.dto.allergy.AllergyManifestationMasterDTO;
import com.doctorclinicapp.backend.dto.allergy.AllergyMasterDataDTO;
import com.doctorclinicapp.backend.dto.allergy.AllergySubstanceMasterDTO;
import com.doctorclinicapp.backend.enums.AllergyCriticality;
import com.doctorclinicapp.backend.enums.AllergyVerificationStatus;
import com.doctorclinicapp.backend.exception.ResourceNotFoundException;
import com.doctorclinicapp.backend.model.Patient;
import com.doctorclinicapp.backend.model.allergy.AllergyManifestationMaster;
import com.doctorclinicapp.backend.model.allergy.AllergySubstanceMaster;
import com.doctorclinicapp.backend.model.allergy.PatientAllergy;
import com.doctorclinicapp.backend.repository.PatientRepository;
import com.doctorclinicapp.backend.repository.allergy.AllergyManifestationMasterRepository;
import com.doctorclinicapp.backend.repository.allergy.AllergySubstanceMasterRepository;
import com.doctorclinicapp.backend.repository.allergy.PatientAllergyRepository;
import com.doctorclinicapp.backend.service.allergy.AllergyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AllergyServiceImpl implements AllergyService {

    private final PatientRepository patientRepository;
    private final PatientAllergyRepository allergyRepo;
    private final AllergySubstanceMasterRepository substanceRepo;
    private final AllergyManifestationMasterRepository manifestationRepo;

    @Override
    public List<AllergyDTO> getAllergiesByPatient(Long patientId) {

        List<PatientAllergy> allergies = allergyRepo.findByPatientId(patientId);

        return allergies.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public AllergyDTO addAllergy(AllergyDTO dto) {

        // A patient having the same substance flagged twice is almost
        // certainly a duplicate entry, not a new allergy — same guard as
        // HistoryServiceImpl.addPastMedicalHistory.
        if (allergyRepo.existsByPatientIdAndSubstanceId(dto.getPatientId(), dto.getSubstanceId())) {
            throw new RuntimeException("This substance is already recorded as an allergy for this patient");
        }

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", dto.getPatientId()));

        AllergySubstanceMaster substance = substanceRepo.findById(dto.getSubstanceId())
                .orElseThrow(() -> new ResourceNotFoundException("AllergySubstance", "id", dto.getSubstanceId()));

        if (dto.getManifestationIds() == null || dto.getManifestationIds().isEmpty()) {
            throw new RuntimeException("At least one manifestation is required");
        }

        List<AllergyManifestationMaster> manifestations =
                manifestationRepo.findAllById(dto.getManifestationIds());

        if (manifestations.size() != dto.getManifestationIds().size()) {
            throw new RuntimeException("One or more manifestations were not found");
        }

        AllergyCriticality criticality;
        AllergyVerificationStatus verificationStatus;
        try {
            criticality = AllergyCriticality.valueOf(dto.getCriticality());
            verificationStatus = AllergyVerificationStatus.valueOf(dto.getVerificationStatus());
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new RuntimeException("Invalid criticality or verification status value");
        }

        PatientAllergy entity = PatientAllergy.builder()
                .patient(patient)
                .substance(substance)
                .criticality(criticality)
                .verificationStatus(verificationStatus)
                .comment(dto.getComment())
                .manifestations(manifestations)
                .build();

        entity = allergyRepo.save(entity);

        return mapToDto(entity);
    }

    @Override
    public void deleteAllergy(Long id) {

        if (!allergyRepo.existsById(id)) {
            throw new ResourceNotFoundException("PatientAllergy", "id", id);
        }

        allergyRepo.deleteById(id);
    }

    @Override
    public AllergyMasterDataDTO getAllergyMasterData() {

        List<AllergySubstanceMasterDTO> substances = substanceRepo.findAll()
                .stream()
                .map(s -> AllergySubstanceMasterDTO.builder()
                        .id(s.getId())
                        .substanceName(s.getSubstanceName())
                        .build())
                .collect(Collectors.toList());

        List<AllergyManifestationMasterDTO> manifestations = manifestationRepo.findAll()
                .stream()
                .map(m -> AllergyManifestationMasterDTO.builder()
                        .id(m.getId())
                        .manifestationName(m.getManifestationName())
                        .build())
                .collect(Collectors.toList());

        return AllergyMasterDataDTO.builder()
                .substances(substances)
                .manifestations(manifestations)
                .criticalityOptions(
                        Arrays.stream(AllergyCriticality.values()).map(Enum::name).collect(Collectors.toList()))
                .verificationStatusOptions(
                        Arrays.stream(AllergyVerificationStatus.values()).map(Enum::name).collect(Collectors.toList()))
                .build();
    }

    private AllergyDTO mapToDto(PatientAllergy entity) {
        return AllergyDTO.builder()
                .id(entity.getId())
                .patientId(entity.getPatient().getId())
                .substanceId(entity.getSubstance().getId())
                .substanceName(entity.getSubstance().getSubstanceName())
                .criticality(entity.getCriticality().name())
                .verificationStatus(entity.getVerificationStatus().name())
                .comment(entity.getComment())
                .manifestationIds(entity.getManifestations().stream()
                        .map(AllergyManifestationMaster::getId)
                        .collect(Collectors.toList()))
                .manifestationNames(entity.getManifestations().stream()
                        .map(AllergyManifestationMaster::getManifestationName)
                        .collect(Collectors.toList()))
                .build();
    }
}
