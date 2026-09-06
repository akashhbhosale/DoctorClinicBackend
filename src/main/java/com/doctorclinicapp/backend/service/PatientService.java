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
        
     // Duplicate ABHA check (exclude same patient)
        if (!patient.getAbhaId().equals(req.getAbhaId())
                && patientRepository.existsByAbhaId(req.getAbhaId())) {
            throw new DuplicateResourceException("ABHA ID already exists");
        }
        
        patient.setAbhaId(req.getAbhaId());
        patient.setFullName(req.getFullName());
        patient.setGender(req.getGender());
        patient.setDateOfBirth(req.getDateOfBirth()); 
        if (req.getDateOfBirth() != null) {
            int age = java.time.Period
                    .between(req.getDateOfBirth(), java.time.LocalDate.now())
                    .getYears();
            patient.setAge(age);
        }
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

    // Delete patient by ID — SOFT delete. Hard-deleting would fail with a
    // foreign key violation for any patient who already has encounters,
    // history, etc. (that data intentionally isn't cascade-deleted, since
    // medical records need to be retrievable even after a patient is
    // "removed"). Flipping active=false hides them from the normal list
    // without touching any of their existing records.
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        patient.setActive(false);
        patientRepository.save(patient);
    }

    // Restore a previously-archived (soft-deleted) patient
    public Patient restorePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        patient.setActive(true);
        return patientRepository.save(patient);
    }

    // List archived (soft-deleted) patients
    public Page<Patient> getArchivedPatients(Pageable pageable) {
        return patientRepository.findAllByActiveFalse(pageable);
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
		return patientRepository.findAllByActiveTrue(pageable);
		}
	
	public Page<Patient> searchPatients(String query, Pageable pageable) {
	    return patientRepository.searchPatients(query, pageable);
	}
}
