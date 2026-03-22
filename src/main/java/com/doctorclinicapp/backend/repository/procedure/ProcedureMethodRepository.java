package com.doctorclinicapp.backend.repository.procedure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.procedure.ProcedureMethodMaster;

public interface ProcedureMethodRepository
        extends JpaRepository<ProcedureMethodMaster, Long> {

    Page<ProcedureMethodMaster> findByNameContainingIgnoreCase(String name, Pageable pageable);
}