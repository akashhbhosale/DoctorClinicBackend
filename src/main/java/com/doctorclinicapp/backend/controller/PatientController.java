package com.doctorclinicapp.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doctorclinicapp.backend.model.Patient;
import com.doctorclinicapp.backend.service.PatientService;

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
	public ResponseEntity<Patient> savePatient(@RequestBody Patient patient) {
		Patient savedPatient = patientService.savePatient(patient);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
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
	// SEARCH BY REGISTRATION NUMBER
	// -------------------------------
	@GetMapping("/registration/{registrationNo}")
	public ResponseEntity<Patient> getByRegistrationNo(@PathVariable String registrationNo) {
		Optional<Patient> patient = patientService.getPatientByRegistrationNo(registrationNo);
		return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// -------------------------------
	// SEARCH BY FULL NAME (contains, ignore case)
	// -------------------------------
	@GetMapping("/search/name")
	public ResponseEntity<List<Patient>> searchByName(@RequestParam String name) {
		List<Patient> patients = patientService.searchByFullName(name);
		return patients.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(patients);
	}
}
