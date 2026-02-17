package com.doctorclinicapp.backend.service;

import com.doctorclinicapp.backend.model.Patient;
import com.doctorclinicapp.backend.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.doctorclinicapp.backend.dto.UpdatePatientRequest;
import com.doctorclinicapp.backend.exception.DuplicateResourceException;
import com.doctorclinicapp.backend.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    // Constructor Injection (recommended way)
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // ---- CRUD OPERATIONS ----

    // Save  a patient
    public Patient savePatient(Patient patient) {

        if (patientRepository.existsByAbhaId(patient.getAbhaId())) {
            throw new DuplicateResourceException("ABHA ID already exists");
        }

        if (patientRepository.existsByPhoneNo(patient.getPhoneNo())) {
            throw new DuplicateResourceException("Phone number already exists");
        }
        
        if (patientRepository.existsByEmail(patient.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        return patientRepository.save(patient);
    }
    
    public Patient updatePatient(Long id, UpdatePatientRequest req) {

        Patient patient = patientRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Patient not found"));


        // Duplicate email check (exclude same patient)
        if (!patient.getEmail().equals(req.getEmail())
                && patientRepository.existsByEmail(req.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        // Duplicate phone check (exclude same patient)
        if (!patient.getPhoneNo().equals(req.getPhoneNo())
                && patientRepository.existsByPhoneNo(req.getPhoneNo())) {
            throw new DuplicateResourceException("Phone number already exists");
        }

        patient.setFullName(req.getFullName());
        patient.setGender(req.getGender());
        patient.setEmail(req.getEmail());
        patient.setPhoneNo(req.getPhoneNo());
        patient.setBloodGroup(req.getBloodGroup());
        patient.setOccupation(req.getOccupation());
        patient.setAddress(req.getAddress());

        return patientRepository.save(patient);
    }


    // Get patient by ID
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    // Delete patient by ID
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    // Get all patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // ---- CUSTOM SEARCH METHODS ----

    // Find patient by ABHA ID
    public Optional<Patient> getPatientByAbhaId(String abhaId) {
        return patientRepository.findByAbhaId(abhaId);
    }

    // Check if ABHA ID exists
    public boolean existsByAbhaId(String abhaId) {
        return patientRepository.existsByAbhaId(abhaId);
    }

    // Find patient by Phone No
    public Optional<Patient> getPatientByPhoneNo(String phoneNo) {
        return patientRepository.findByPhoneNo(phoneNo);
    }

    // Check if phone number exists
    public boolean existsByPhoneNo(String phoneNo) {
        return patientRepository.existsByPhoneNo(phoneNo);
    }

   
    // Find patients by name (case-insensitive contains)
    public Page<Patient> searchPatientsByName(String name, Pageable pageable) {
        return patientRepository.findByFullNameContainingIgnoreCase(name, pageable);
        }

	public Page<Patient> getAllPatients(Pageable pageable) {
		return patientRepository.findAll(pageable);
		}
	
	public Page<Patient> searchPatients(String query, Pageable pageable) {
	    return patientRepository.searchPatients(query, pageable);
	}

}
