package com.doctorclinicapp.backend.service.medication;

import java.util.List;

import com.doctorclinicapp.backend.dto.medication.MedicationHistoryEntryResponse;

public interface MedicationHistoryService {

    List<MedicationHistoryEntryResponse> getMedicationHistoryByPatient(Long patientId);
}
