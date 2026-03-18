package com.doctorclinicapp.backend.repository.history;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctorclinicapp.backend.model.history.PatientPastMedicalHistory;

@Repository
public interface PatientPastMedicalHistoryRepository
        extends JpaRepository<PatientPastMedicalHistory, Long> {

    List<PatientPastMedicalHistory> findByPatientId(Long patientId);
    
    // Restric Duplicate entries
    boolean existsByPatientIdAndHistoryId(Long patientId, Long historyId);

}