package com.doctorclinicapp.backend.repository.history;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctorclinicapp.backend.model.history.PatientFamilyHistory;

@Repository
public interface PatientFamilyHistoryRepository
        extends JpaRepository<PatientFamilyHistory, Long> {

    List<PatientFamilyHistory> findByPatientId(Long patientId);

}