package com.doctorclinicapp.backend.repository.medication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.enums.MedicineType;
import com.doctorclinicapp.backend.model.medication.MedicineMaster;

public interface MedicineMasterRepository extends JpaRepository<MedicineMaster, Long> {

    Page<MedicineMaster> findByMedicineTypeAndMedicineNameContainingIgnoreCase(
            MedicineType medicineType, String medicineName, Pageable pageable);

    Page<MedicineMaster> findByMedicineType(MedicineType medicineType, Pageable pageable);
}
