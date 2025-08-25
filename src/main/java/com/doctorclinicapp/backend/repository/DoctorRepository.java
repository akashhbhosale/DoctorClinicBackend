package com.doctorclinicapp.backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.enums.DoctorSpeciality;
import com.doctorclinicapp.backend.model.Doctor;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	Optional<Doctor> findByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	boolean existsByPhoneNo(String phoneNo);

	// NEW: list doctors by speciality
	List<Doctor> findBySpeciality(DoctorSpeciality speciality);

	// NEW: case‑insensitive “contains” search on full name
	List<Doctor> findByFullNameContainingIgnoreCase(String fullNamePart);
}
