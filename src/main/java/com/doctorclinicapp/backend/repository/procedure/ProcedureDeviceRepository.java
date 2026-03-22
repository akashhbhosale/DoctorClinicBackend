package com.doctorclinicapp.backend.repository.procedure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.procedure.ProcedureDeviceMaster;

public interface ProcedureDeviceRepository 
        extends JpaRepository<ProcedureDeviceMaster, Long> {

    Page<ProcedureDeviceMaster> findByNameContainingIgnoreCase(String name, Pageable pageable);
}