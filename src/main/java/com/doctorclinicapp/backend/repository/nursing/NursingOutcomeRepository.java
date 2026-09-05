package com.doctorclinicapp.backend.repository.nursing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.nursing.NursingOutcomeMaster;

public interface NursingOutcomeRepository extends JpaRepository<NursingOutcomeMaster, Long> {

    Page<NursingOutcomeMaster> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
