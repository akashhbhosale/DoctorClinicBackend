package com.doctorclinicapp.backend.repository.allergy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.allergy.AllergySubstanceMaster;

public interface AllergySubstanceMasterRepository
        extends JpaRepository<AllergySubstanceMaster, Long> {
}
