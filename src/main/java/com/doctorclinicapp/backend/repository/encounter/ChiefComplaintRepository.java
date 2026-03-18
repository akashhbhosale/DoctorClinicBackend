package com.doctorclinicapp.backend.repository.encounter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.doctorclinicapp.backend.model.encounter.ChiefComplaint;

public interface ChiefComplaintRepository 
        extends JpaRepository<ChiefComplaint, Long> {

    Page<ChiefComplaint> findByComplaintNameContainingIgnoreCase(
            String complaintName, Pageable pageable);

}