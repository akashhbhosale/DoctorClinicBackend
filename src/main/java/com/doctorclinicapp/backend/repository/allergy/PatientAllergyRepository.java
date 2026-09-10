package com.doctorclinicapp.backend.repository.allergy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.allergy.PatientAllergy;

public interface PatientAllergyRepository extends JpaRepository<PatientAllergy, Long> {

    List<PatientAllergy> findByPatientId(Long patientId);

    boolean existsByPatientIdAndSubstanceId(Long patientId, Long substanceId);
}
