package com.doctorclinicapp.backend.service.encounter;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.exception.ResourceNotFoundException;
import com.doctorclinicapp.backend.model.Doctor;
import com.doctorclinicapp.backend.model.Patient;
import com.doctorclinicapp.backend.model.encounter.Encounter;
import com.doctorclinicapp.backend.repository.DoctorRepository;
import com.doctorclinicapp.backend.repository.PatientRepository;
import com.doctorclinicapp.backend.repository.encounter.EncounterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncounterService {

    private final EncounterRepository encounterRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

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
}