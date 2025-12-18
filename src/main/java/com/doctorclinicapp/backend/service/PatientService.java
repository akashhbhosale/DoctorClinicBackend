package com.doctorclinicapp.backend.service;

import com.doctorclinicapp.backend.model.Patient;
import com.doctorclinicapp.backend.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    // Constructor Injection (recommended way)
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // ---- CRUD OPERATIONS ----

    // Save or update a patient
    public Patient savePatient(Patient patient) {
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

    // Find patient by Registration No
    public Optional<Patient> getPatientByRegistrationNo(String registrationNo) {
        return patientRepository.findByRegistrationNo(registrationNo);
    }

    // Check if registration number exists
    public boolean existsByRegistrationNo(String registrationNo) {
        return patientRepository.existsByRegistrationNo(registrationNo);
    }

    // Find patients by name (case-insensitive contains)
    public List<Patient> searchByFullName(String namePart) {
        return patientRepository.findByFullNameContainingIgnoreCase(namePart);
    }
}
