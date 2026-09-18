package com.doctorclinicapp.backend.repository.medication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.medication.RouteOfAdministrationMaster;

public interface RouteOfAdministrationMasterRepository extends JpaRepository<RouteOfAdministrationMaster, Long> {

    Page<RouteOfAdministrationMaster> findByRouteNameContainingIgnoreCase(String routeName, Pageable pageable);
}
