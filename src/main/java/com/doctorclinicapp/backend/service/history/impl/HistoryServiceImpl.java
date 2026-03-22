package com.doctorclinicapp.backend.service.history.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.history.FamilyHistoryDTO;
import com.doctorclinicapp.backend.dto.history.FamilyHistoryMasterDTO;
import com.doctorclinicapp.backend.dto.history.HistoryMasterDataDTO;
import com.doctorclinicapp.backend.dto.history.HistoryResponseDTO;
import com.doctorclinicapp.backend.dto.history.PastMedicalHistoryDTO;
import com.doctorclinicapp.backend.dto.history.PastMedicalHistoryMasterDTO;
import com.doctorclinicapp.backend.dto.history.RelationshipMasterDTO;
import com.doctorclinicapp.backend.model.Patient;
import com.doctorclinicapp.backend.model.history.FamilyHistoryMaster;
import com.doctorclinicapp.backend.model.history.PastMedicalHistoryMaster;
import com.doctorclinicapp.backend.model.history.PatientFamilyHistory;
import com.doctorclinicapp.backend.model.history.PatientPastMedicalHistory;
import com.doctorclinicapp.backend.model.history.RelationshipMaster;
import com.doctorclinicapp.backend.repository.PatientRepository;
import com.doctorclinicapp.backend.repository.history.FamilyHistoryMasterRepository;
import com.doctorclinicapp.backend.repository.history.PastMedicalHistoryMasterRepository;
import com.doctorclinicapp.backend.repository.history.PatientFamilyHistoryRepository;
import com.doctorclinicapp.backend.repository.history.PatientPastMedicalHistoryRepository;
import com.doctorclinicapp.backend.repository.history.RelationshipMasterRepository;
import com.doctorclinicapp.backend.service.history.HistoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final PatientRepository patientRepository;
    private final PatientPastMedicalHistoryRepository pastHistoryRepo;
    private final PatientFamilyHistoryRepository familyHistoryRepo;
    private final PastMedicalHistoryMasterRepository pastMasterRepo;
    private final FamilyHistoryMasterRepository familyMasterRepo;
    private final RelationshipMasterRepository relationshipRepo;

    @Override
    public List<PastMedicalHistoryDTO> getPastMedicalHistory(Long patientId) {

        List<PatientPastMedicalHistory> historyList =
                pastHistoryRepo.findByPatientId(patientId);

        return historyList.stream().map(history ->

            PastMedicalHistoryDTO.builder()
                    .id(history.getId())
                    .patientId(history.getPatient().getId())
                    .historyId(history.getHistory().getId())
                    .historyName(history.getHistory().getHistoryName())
                    .years(history.getYears())
                    .months(history.getMonths())
                    .days(history.getDays())
                    .build()
        ).collect(Collectors.toList());
    }
    
    @Override
    public PastMedicalHistoryDTO addPastMedicalHistory(PastMedicalHistoryDTO dto) {
    	
    	//checking of duplicate medical history addition
    	if(pastHistoryRepo.existsByPatientIdAndHistoryId(dto.getPatientId(), dto.getHistoryId())){
    	    throw new RuntimeException("History already exists for this patient");
    	}

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        PastMedicalHistoryMaster historyMaster =
                pastMasterRepo.findById(dto.getHistoryId())
                .orElseThrow(() -> new RuntimeException("History not found"));

        PatientPastMedicalHistory entity = PatientPastMedicalHistory.builder()
                .patient(patient)
                .history(historyMaster)
                .years(dto.getYears())
                .months(dto.getMonths())
                .days(dto.getDays())
                .build();

        entity = pastHistoryRepo.save(entity);

        dto.setId(entity.getId());
        dto.setHistoryName(historyMaster.getHistoryName());

        return dto;
    }
    
    @Override
    public void deletePastMedicalHistory(Long id) {

        if(!pastHistoryRepo.existsById(id)) {
            throw new RuntimeException("Past history record not found");
        }

        pastHistoryRepo.deleteById(id);
    }
    
    @Override
    public List<FamilyHistoryDTO> getFamilyHistory(Long patientId) {

        List<PatientFamilyHistory> historyList =
                familyHistoryRepo.findByPatientId(patientId);

        return historyList.stream().map(history ->

            FamilyHistoryDTO.builder()
                    .id(history.getId())
                    .patientId(history.getPatient().getId())
                    .historyId(history.getHistory().getId())
                    .historyName(history.getHistory().getHistoryName())
                    .relationshipId(history.getRelationship().getId())
                    .relationshipName(history.getRelationship().getRelationshipName())
                    .build()

        ).collect(Collectors.toList());
    }
    
    @Override
    public FamilyHistoryDTO addFamilyHistory(FamilyHistoryDTO dto) {

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        FamilyHistoryMaster historyMaster =
                familyMasterRepo.findById(dto.getHistoryId())
                .orElseThrow(() -> new RuntimeException("Family history not found"));

        RelationshipMaster relationship =
                relationshipRepo.findById(dto.getRelationshipId())
                .orElseThrow(() -> new RuntimeException("Relationship not found"));

        PatientFamilyHistory entity = PatientFamilyHistory.builder()
                .patient(patient)
                .history(historyMaster)
                .relationship(relationship)
                .build();

        entity = familyHistoryRepo.save(entity);

        dto.setId(entity.getId());
        dto.setHistoryName(historyMaster.getHistoryName());
        dto.setRelationshipName(relationship.getRelationshipName());

        return dto;
    }
    
    @Override
    public void deleteFamilyHistory(Long id) {

        if(!familyHistoryRepo.existsById(id)) {
            throw new RuntimeException("Family history record not found");
        }

        familyHistoryRepo.deleteById(id);
    }
    
    //HistoryResponseDTO returns all History in one call
    @Override
    public HistoryResponseDTO getPatientHistory(Long patientId) {

        List<PastMedicalHistoryDTO> past =
                getPastMedicalHistory(patientId);

        List<FamilyHistoryDTO> family =
                getFamilyHistory(patientId);

        return HistoryResponseDTO.builder()
                .pastMedicalHistory(past)
                .familyHistory(family)
                .build();
    }


	@Override
	public HistoryMasterDataDTO getHistoryMasterData() {

	    List<PastMedicalHistoryMasterDTO> past =
	            pastMasterRepo.findAll()
	                    .stream()
	                    .map(h -> PastMedicalHistoryMasterDTO.builder()
	                            .id(h.getId())
	                            .historyName(h.getHistoryName())
	                            .build())
	                    .collect(Collectors.toList());

	    List<FamilyHistoryMasterDTO> family =
	            familyMasterRepo.findAll()
	                    .stream()
	                    .map(h -> FamilyHistoryMasterDTO.builder()
	                            .id(h.getId())
	                            .historyName(h.getHistoryName())
	                            .build())
	                    .collect(Collectors.toList());

	    List<RelationshipMasterDTO> relationships =
	            relationshipRepo.findAll()
	                    .stream()
	                    .map(r -> RelationshipMasterDTO.builder()
	                            .id(r.getId())
	                            .relationshipName(r.getRelationshipName())
	                            .build())
	                    .collect(Collectors.toList());

	    return HistoryMasterDataDTO.builder()
	            .pastMedicalHistory(past)
	            .familyHistory(family)
	            .relationships(relationships)
	            .build();
	}
    }