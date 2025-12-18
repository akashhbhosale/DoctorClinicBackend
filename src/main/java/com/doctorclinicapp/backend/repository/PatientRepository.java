package com.doctorclinicapp.backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.doctorclinicapp.backend.model.Patient;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Find patient by ABHA ID (unique)
    Optional<Patient> findByAbhaId(String abhaId);

    // Check if a patient with a given ABHA ID exists
    boolean existsByAbhaId(String abhaId);

    // Find patient by phone number (unique)
    Optional<Patient> findByPhoneNo(String phoneNo);

    // Check if a patient with a given phone number exists
    boolean existsByPhoneNo(String phoneNo);

    // Find patient by registration number (unique)
    Optional<Patient> findByRegistrationNo(String registrationNo);

    // Check if a patient with a given registration number exists
    boolean existsByRegistrationNo(String registrationNo);

    // Find patient by full name (case-insensitive)
    List<Patient> findByFullNameContainingIgnoreCase(String fullNamePart);

    // Additional custom queries can be added as needed
}
