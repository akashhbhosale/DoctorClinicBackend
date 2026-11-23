package com.doctorclinicapp.backend.model.encounter;

import java.time.LocalDateTime;

import com.doctorclinicapp.backend.model.Doctor;
import com.doctorclinicapp.backend.model.Patient;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

	@Column(name = "visit_status", length = 16)
	private String visitStatus;

	@JsonIgnore
	@ToString.Exclude
	@Column(name = "final_record", columnDefinition = "LONGTEXT")
	private String finalRecord;

	// Link to Patient
	@ManyToOne
	@JoinColumn(name = "patient_id", nullable = false)
	private Patient patient;

	// Link to Doctor
	@ManyToOne
	@JoinColumn(name = "doctor_id", nullable = false)
	private Doctor doctor;

	// Encounter date and timing
	@Column(name = "encounter_date_time", nullable = false)
	private LocalDateTime encounterDate;

	// Encounter Notes
	@Column(name = "notes", columnDefinition = "TEXT")
	private String notes;
}