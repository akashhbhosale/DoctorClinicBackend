package com.doctorclinicapp.backend.repository.procedure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.procedure.ProcedureMaster;

public interface ProcedureMasterRepository extends JpaRepository<ProcedureMaster, Long> {

    Page<ProcedureMaster> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<ProcedureMaster> findByCodeContainingIgnoreCase(String code, Pageable pageable);

    Page<ProcedureMaster> findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(
            String name, String code, Pageable pageable);
}