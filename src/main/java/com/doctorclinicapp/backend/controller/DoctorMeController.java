package com.doctorclinicapp.backend.controller;

import com.doctorclinicapp.backend.enums.DoctorSpeciality;
import com.doctorclinicapp.backend.model.Doctor;
import com.doctorclinicapp.backend.repository.DoctorRepository;
import com.doctorclinicapp.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorMeController {

    private final JwtService jwtService;
    private final DoctorRepository doctorRepository;

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("message", "Missing or invalid Authorization header"));
        }

        String token = authHeader.substring(7);
        String username;
        try {
            username = jwtService.extractUsername(token);
        } catch (Exception ex) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid token"));
        }

        return doctorRepository.findByUsername(username)
            .<ResponseEntity<?>>map(d -> ResponseEntity.ok(Map.of(
                "id", d.getId(),
                "username", d.getUsername(),
                "registrationNo", d.getRegistrationNo(),
                "qualification", d.getQualification(),
                "speciality", d.getSpeciality(),
                "email", d.getEmail(),
                "phoneNo", d.getPhoneNo()
            )))
            .orElseGet(() -> ResponseEntity.status(404).body(Map.of("message", "Doctor not found")));
    }
    
 // GET /api/doctors/speciality/{speciality} -> list by speciality
    @GetMapping("/speciality/{speciality}")
    public ResponseEntity<?> bySpeciality(@PathVariable String speciality) {
        try {
            DoctorSpeciality spec = DoctorSpeciality.valueOf(speciality.toUpperCase());
            List<Doctor> doctors = doctorRepository.findBySpeciality(spec);
            return ResponseEntity.ok(doctors);
        } catch (IllegalArgumentException ex) {
            // Invalid enum value
            String valid = Arrays.toString(DoctorSpeciality.values());
            return ResponseEntity.badRequest().body(
                java.util.Map.of(
                    "message", "Invalid speciality: " + speciality,
                    "validValues", valid
                )
            );
        }
    }
}
