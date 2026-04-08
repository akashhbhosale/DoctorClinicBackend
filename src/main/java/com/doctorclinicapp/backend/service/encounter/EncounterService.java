package com.doctorclinicapp.backend.service.encounter;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.encounter.DeleteEncounterResponse;
import com.doctorclinicapp.backend.dto.encounter.EncounterChiefComplaintResponse;
import com.doctorclinicapp.backend.dto.encounter.EncounterDetailsResponse;
import com.doctorclinicapp.backend.dto.encounter.EncounterDiagnosisResponse;
import com.doctorclinicapp.backend.dto.encounter.EncounterHistoryResponse;
import com.doctorclinicapp.backend.dto.encounter.EncounterProcedureResponse;
import com.doctorclinicapp.backend.exception.ResourceNotFoundException;
import com.doctorclinicapp.backend.model.Doctor;
import com.doctorclinicapp.backend.model.Patient;
import com.doctorclinicapp.backend.model.encounter.Encounter;
import com.doctorclinicapp.backend.repository.DoctorRepository;
import com.doctorclinicapp.backend.repository.PatientRepository;
import com.doctorclinicapp.backend.repository.encounter.EncounterChiefComplaintRepository;
import com.doctorclinicapp.backend.repository.encounter.EncounterDiagnosisRepository;
import com.doctorclinicapp.backend.repository.encounter.EncounterProcedureRepository;
import com.doctorclinicapp.backend.repository.encounter.EncounterRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EncounterService {

    private final EncounterRepository encounterRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final EncounterChiefComplaintRepository encounterChiefComplaintRepository;
    private final EncounterDiagnosisRepository encounterDiagnosisRepository;
    private final EncounterProcedureRepository encounterProcedureRepository;

    public Encounter createEncounter(Long patientId, Long doctorId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        Encounter encounter = Encounter.builder()
                .patient(patient)
                .doctor(doctor)
                .encounterDate(LocalDateTime.now())
                .build();

        return encounterRepository.save(encounter);
    }
    
    // Method for Encounter Notes
    public Encounter updateNotes(Long encounterId, String notes) {

        Encounter encounter = encounterRepository.findById(encounterId)
                .orElseThrow(() -> new ResourceNotFoundException("Encounter not found"));

        encounter.setNotes(notes);

        return encounterRepository.save(encounter);
    }

    public Encounter getEncounterById(Long id) {
        return encounterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Encounter not found"));
    }
    
    // Full Encounter response method
    public EncounterDetailsResponse getEncounterDetails(Long encounterId) {

        Encounter encounter = encounterRepository.findById(encounterId)
                .orElseThrow(() -> new ResourceNotFoundException("Encounter not found"));

        return EncounterDetailsResponse.builder()
                .encounterId(encounter.getId())
                .patientId(encounter.getPatient().getId())
                .patientName(encounter.getPatient().getFullName())
                .doctorId(encounter.getDoctor().getId())
                .doctorName(encounter.getDoctor().getFullName())
                .encounterDate(encounter.getEncounterDate())
                .notes(encounter.getNotes())
                .complaints(
                	    encounterChiefComplaintRepository.findByEncounterId(encounterId)
                	        .stream()
                	        .map(c -> new EncounterChiefComplaintResponse(
                	                c.getId(),
                	                c.getChiefComplaint().getComplaintName(),
                	                c.getTimeSinceDays()
                	        ))
                	        .toList()
                	)   // we will fill next
                .diagnoses(
                	    encounterDiagnosisRepository.findByEncounter_Id(encounterId)
                	        .stream()
                	        .map(d -> new EncounterDiagnosisResponse(
                	                d.getId(),
                	                d.getDiagnosisType(),
                	                d.getIcdCode().getCode(),
                	                d.getIcdCode().getDescription()
                	        ))
                	        .toList()
                	)   // we will fill next
                .procedures(
                	    encounterProcedureRepository.findByEncounterId(encounterId)
                	        .stream()
                	        .map(p -> EncounterProcedureResponse.builder()
                	                .id(p.getId())
                	                .procedureId(p.getProcedure().getId())
                	                .procedureName(p.getProcedure().getName())
                	                .site(p.getSite() != null ? p.getSite().getName() : null)
                	                .device(p.getDevice() != null ? p.getDevice().getName() : null)
                	                .method(p.getMethod() != null ? p.getMethod().getName() : null)
                	                .laterality(p.getLaterality() != null ? p.getLaterality().name() : null)
                	                .priority(p.getPriority() != null ? p.getPriority().name() : null)
                	                .build()
                	        )
                	        .toList()
                	) // we will fill next
                .build();
    }
    
    //Delete All Method used in it
    public DeleteEncounterResponse deleteEncounter(Long encounterId) {

        Encounter encounter = encounterRepository.findById(encounterId)
                .orElseThrow(() -> new ResourceNotFoundException("Encounter not found"));

        // Delete child records first
        encounterChiefComplaintRepository.deleteByEncounterId(encounterId);
        encounterDiagnosisRepository.deleteByEncounter_Id(encounterId);
        encounterProcedureRepository.deleteByEncounterId(encounterId);

        // Delete main encounter
        encounterRepository.delete(encounter);

        return new DeleteEncounterResponse("Encounter and all related data deleted successfully");
    }
    
    // To get list of previous History of encounters 
    public List<EncounterHistoryResponse> getEncounterHistoryByPatientId(Long patientId) {

        patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        return encounterRepository.findByPatientIdOrderByEncounterDateDesc(patientId)
                .stream()
                .map(encounter -> EncounterHistoryResponse.builder()
                        .id(encounter.getId())
                        .encounterDate(encounter.getEncounterDate())
                        .doctorId(encounter.getDoctor() != null ? encounter.getDoctor().getId() : null)
                        .doctorName(encounter.getDoctor() != null ? encounter.getDoctor().getFullName() : null)
                        .notes(encounter.getNotes())
                        .build())
                .toList();
    }
}