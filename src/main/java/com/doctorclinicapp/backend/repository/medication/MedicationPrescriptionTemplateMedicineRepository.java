
package com.doctorclinicapp.backend.repository.medication;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.medication.MedicationPrescriptionTemplateMedicine;

public interface MedicationPrescriptionTemplateMedicineRepository
        extends JpaRepository<MedicationPrescriptionTemplateMedicine, Long> {

	List<MedicationPrescriptionTemplateMedicine> findByPrescriptionTemplate_Id(Long templateId);

	void deleteByPrescriptionTemplate_Id(Long templateId);
}
