package com.doctorclinicapp.backend.repository.allergy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.allergy.AllergyManifestationMaster;

public interface AllergyManifestationMasterRepository
        extends JpaRepository<AllergyManifestationMaster, Long> {
}
