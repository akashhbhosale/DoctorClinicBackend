package com.doctorclinicapp.backend.controller;

import com.doctorclinicapp.backend.model.Doctor;
import com.doctorclinicapp.backend.repository.DoctorRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorRepository doctorRepository;
    
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;


    // GET /api/doctors -> list all doctors
    @GetMapping
    public List<Doctor> list() {
        return doctorRepository.findAll();   // password won't be returned (write-only)
    }
    
    // GET /api/doctors/{id} -> get one doctor
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        return doctorRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(
                        java.util.Map.of("message", "Doctor not found")
                ));
    }
    
    // 🔍 Search doctors by name (case-insensitive, partial match)
    @GetMapping("/search")
    public ResponseEntity<List<Doctor>> searchByName(@RequestParam String name) {
        List<Doctor> results = doctorRepository.findByFullNameContainingIgnoreCase(name);
        return ResponseEntity.ok(results);
    }
    
 // PUT/PATCH: set or change a doctor's full name
    @PatchMapping("/{id}/name")
    public ResponseEntity<?> updateName(
            @PathVariable Long id,
            @RequestBody java.util.Map<String, String> body) {

        String fullName = body.get("fullName");
        if (fullName == null || fullName.isBlank() || fullName.length() > 80) {
            return ResponseEntity.badRequest().body(java.util.Map.of(
                    "message", "fullName is required (1–80 chars)"
            ));
        }

        return doctorRepository.findById(id)
                .map(doc -> {
                    doc.setFullName(fullName.trim());
                    doctorRepository.save(doc);
                    return ResponseEntity.ok(java.util.Map.of(
                            "message", "Name updated",
                            "id", doc.getId(),
                            "fullName", doc.getFullName()
                    ));
                })
                .orElseGet(() -> ResponseEntity.status(404).body(
                        java.util.Map.of("message", "Doctor not found")
                ));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody com.doctorclinicapp.backend.dto.UpdateDoctorRequest req) {

        return doctorRepository.findById(id).map(existing -> {
            // uniqueness checks only if the value actually changes
            if (!existing.getUsername().equals(req.getUsername())
                    && doctorRepository.existsByUsername(req.getUsername())) {
                return ResponseEntity.status(409).body(java.util.Map.of("message", "Username already exists"));
            }
            if (!existing.getEmail().equalsIgnoreCase(req.getEmail())
                    && doctorRepository.existsByEmail(req.getEmail())) {
                return ResponseEntity.status(409).body(java.util.Map.of("message", "Email already exists"));
            }
            if (!existing.getPhoneNo().equals(req.getPhoneNo())
                    && doctorRepository.existsByPhoneNo(req.getPhoneNo())) {
                return ResponseEntity.status(409).body(java.util.Map.of("message", "Phone number already exists"));
            }

            // require speciality for full update
            if (req.getSpeciality() == null) {
                return ResponseEntity.badRequest().body(java.util.Map.of("message", "speciality is required"));
            }

            // apply updates
            existing.setUsername(req.getUsername());
            existing.setQualification(req.getQualification());
            existing.setSpeciality(req.getSpeciality());
            existing.setFullName(req.getFullName());
            existing.setEmail(req.getEmail());
            existing.setPhoneNo(req.getPhoneNo());

            // always re-encode the new password on full update
            existing.setPassword(passwordEncoder.encode(req.getPassword()));

            Doctor saved = doctorRepository.save(existing);
            return ResponseEntity.ok(java.util.Map.of(
                    "message", "Doctor updated",
                    "id", saved.getId()
            ));
        }).orElseGet(() -> ResponseEntity.status(404).body(java.util.Map.of("message", "Doctor not found")));
    }
    
    // ********* Deleteby Id **********
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        if (!doctorRepository.existsById(id)) {
            return ResponseEntity.status(404).body(java.util.Map.of("message", "Doctor not found"));
        }
        doctorRepository.deleteById(id);
        return ResponseEntity.ok(java.util.Map.of("message", "Doctor deleted", "id", id));
    }


}
