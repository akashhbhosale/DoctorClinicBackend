package com.doctorclinicapp.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doctorclinicapp.backend.dto.CreatePatientRequest;
import com.doctorclinicapp.backend.dto.PatientResponse;
import com.doctorclinicapp.backend.dto.UpdatePatientRequest;
import com.doctorclinicapp.backend.model.Patient;
import com.doctorclinicapp.backend.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

	private final PatientService patientService;

	// Constructor Injection
	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

	// -------------------------------
	// CREATE / UPDATE PATIENT
	// -------------------------------
	@PostMapping("/save")
	public ResponseEntity<Patient> savePatient(
	        @RequestBody @jakarta.validation.Valid CreatePatientRequest req) {

	    Patient patient = Patient.builder()
	            .abhaId(req.getAbhaId())
	            .fullName(req.getFullName())
	            .gender(req.getGender())
	            .dateOfBirth(req.getDateOfBirth())
	            .email(req.getEmail())
	            .phoneNo(req.getPhoneNo())
	            .age(req.getAge())
	            .occupation(req.getOccupation())
	            .bloodGroup(req.getBloodGroup())
	            .address(req.getAddress())
	            .build();

	    Patient savedPatient = patientService.savePatient(patient);
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<PatientResponse> updatePatient(
	        @PathVariable Long id,
	        @RequestBody @Valid UpdatePatientRequest req) {

	    Patient updated = patientService.updatePatient(id, req);

	    return ResponseEntity.ok(PatientResponse.fromEntity(updated));
	}


	// -------------------------------
	// GET ALL PATIENTS
	// -------------------------------
	@GetMapping("/all")
	public ResponseEntity<List<Patient>> getAllPatients() {
		List<Patient> patients = patientService.getAllPatients();
		return ResponseEntity.ok(patients);
	}

	// -------------------------------
	// GET PATIENT BY ID
	// -------------------------------
	@GetMapping("/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
		Optional<Patient> patient = patientService.getPatientById(id);
		return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// -------------------------------
	// DELETE PATIENT BY ID
	// -------------------------------
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
		patientService.deletePatient(id);
		return ResponseEntity.noContent().build();
	}

	// -------------------------------
	// SEARCH BY ABHA ID
	// -------------------------------
	@GetMapping("/abha/{abhaId}")
	public ResponseEntity<Patient> getByAbhaId(@PathVariable String abhaId) {
		Optional<Patient> patient = patientService.getPatientByAbhaId(abhaId);
		return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// -------------------------------
	// SEARCH BY PHONE NUMBER
	// -------------------------------
	@GetMapping("/phone/{phoneNo}")
	public ResponseEntity<Patient> getByPhoneNo(@PathVariable String phoneNo) {
		Optional<Patient> patient = patientService.getPatientByPhoneNo(phoneNo);
		return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// -------------------------------
	// SEARCH BY FULL NAME (PAGINATED)
	// -------------------------------
	@GetMapping("/search/name")
	public ResponseEntity<Page<PatientResponse>> searchByName(
	        @RequestParam String name,
	        Pageable pageable) {

	    Page<Patient> patientPage =
	            patientService.searchPatientsByName(name, pageable);

	    Page<PatientResponse> responsePage =
	            patientPage.map(PatientResponse::fromEntity);

	    return ResponseEntity.ok(responsePage);
	}

}
