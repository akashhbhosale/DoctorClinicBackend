package com.doctorclinicapp.backend.model.encounter;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.doctorclinicapp.backend.model.Doctor;
import com.doctorclinicapp.backend.model.Patient;

@Entity
@Table(name = "encounters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Encounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to Patient
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Link to Doctor
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    
    //Encounter date and timing
    @Column(name = "encounter_date_time", nullable = false)
    private LocalDateTime encounterDate;
    
  //Encounter Notes
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}