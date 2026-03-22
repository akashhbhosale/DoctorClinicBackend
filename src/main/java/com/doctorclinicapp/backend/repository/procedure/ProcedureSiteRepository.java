package com.doctorclinicapp.backend.repository.procedure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.procedure.ProcedureSiteMaster;

public interface ProcedureSiteRepository extends JpaRepository<ProcedureSiteMaster, Long> {

    Page<ProcedureSiteMaster> findByNameContainingIgnoreCase(String name, Pageable pageable);
}