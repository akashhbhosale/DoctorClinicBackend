package com.doctorclinicapp.backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.doctorclinicapp.backend.model.Patient;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Find patient by ABHA ID (unique)
    Optional<Patient> findByAbhaId(String abhaId);

    // Check if a patient with a given ABHA ID exists
    boolean existsByAbhaId(String abhaId);

    // Find patient by phone number (unique)
    Optional<Patient> findByPhoneNo(String phoneNo);

    // Check if a patient with a given phone number exists
    boolean existsByPhoneNo(String phoneNo);
    
 // Check if a patient with a given phone number exists
    boolean existsByEmail(String email);

    // Find patient by full name (case-insensitive)
    Page<Patient> findByFullNameContainingIgnoreCase(String fullNamePart, Pageable pageable);
    
    @Query("""
    	    SELECT p FROM Patient p
    	    WHERE LOWER(p.fullName) LIKE LOWER(CONCAT('%', :query, '%'))
    	       OR p.phoneNo LIKE CONCAT('%', :query, '%')
    	       OR LOWER(p.abhaId) LIKE LOWER(CONCAT('%', :query, '%'))
    	""")
    	Page<Patient> searchPatients(@Param("query") String query, Pageable pageable);
  
}
