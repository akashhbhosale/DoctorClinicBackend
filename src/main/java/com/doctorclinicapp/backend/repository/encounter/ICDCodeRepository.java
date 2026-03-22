package com.doctorclinicapp.backend.repository.encounter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.doctorclinicapp.backend.model.encounter.ICDCode;

public interface ICDCodeRepository 
        extends JpaRepository<ICDCode, Long> {

    Page<ICDCode> findByCodeContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String code,
            String description,
            Pageable pageable);
}