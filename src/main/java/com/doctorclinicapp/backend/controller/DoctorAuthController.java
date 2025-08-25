package com.doctorclinicapp.backend.controller;

import com.doctorclinicapp.backend.dto.LoginRequest;
import com.doctorclinicapp.backend.model.Doctor;
import com.doctorclinicapp.backend.repository.DoctorRepository;
import com.doctorclinicapp.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class DoctorAuthController {

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;   // <-- NEW

    // --- Register Doctor ---
    @PostMapping("/register")
    public ResponseEntity<?> registerDoctor(@Valid @RequestBody Doctor request) {
        if (doctorRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(409).body(Map.of("message", "Username already exists"));
        }
        if (doctorRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(409).body(Map.of("message", "Email already exists"));
        }
        if (doctorRepository.existsByPhoneNo(request.getPhoneNo())) {
            return ResponseEntity.status(409).body(Map.of("message", "Phone number already exists"));
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Doctor saved = doctorRepository.save(request);

        return ResponseEntity.status(201).body(Map.of(
                "message", "Registered successfully",
                "id", saved.getId()
        ));
    }

    // --- Login Doctor ---
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Optional<Doctor> opt = doctorRepository.findByUsername(request.getUsername());
        if (opt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }

        Doctor doctor = opt.get();
        if (!passwordEncoder.matches(request.getPassword(), doctor.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }

        // Generate JWT
        String token = jwtService.generateToken(doctor.getUsername());

        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "token", token,
                "doctor", Map.of(
                        "id", doctor.getId(),
                        "username", doctor.getUsername(),
                        "registrationNo", doctor.getRegistrationNo(),
                        "qualification", doctor.getQualification(),
                        "speciality", doctor.getSpeciality(),
                        "email", doctor.getEmail(),
                        "phoneNo", doctor.getPhoneNo()
                )
        ));
    }
}
