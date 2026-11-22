
package com.doctorclinicapp.backend.repository.medication;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.medication.PrescriptionTemplate;

public interface MedicationPrescriptionTemplateRepository
        extends JpaRepository<PrescriptionTemplate, Long> {

    List<PrescriptionTemplate> findByDoctor_Id(Long doctorId);
}
