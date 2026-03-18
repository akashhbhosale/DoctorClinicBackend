package com.doctorclinicapp.backend.controller.history;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.doctorclinicapp.backend.dto.history.PastMedicalHistoryDTO;
import com.doctorclinicapp.backend.dto.history.FamilyHistoryDTO;
import com.doctorclinicapp.backend.dto.history.HistoryMasterDataDTO;
import com.doctorclinicapp.backend.dto.history.HistoryResponseDTO;
import com.doctorclinicapp.backend.service.history.HistoryService;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

	private final HistoryService historyService;

	// Get Past Medical History
	@GetMapping("/past/{patientId}")
	public List<PastMedicalHistoryDTO> getPastMedicalHistory(@PathVariable Long patientId) {

		return historyService.getPastMedicalHistory(patientId);
	}

	// Add Past Medical History
	@PostMapping("/past")
	public PastMedicalHistoryDTO addPastMedicalHistory(@RequestBody PastMedicalHistoryDTO dto) {

		return historyService.addPastMedicalHistory(dto);
	}

	// Delete Past Medical History
	@DeleteMapping("/past/{id}")
	public void deletePastMedicalHistory(@PathVariable Long id) {

		historyService.deletePastMedicalHistory(id);
	}

	// Get Family History
	@GetMapping("/family/{patientId}")
	public List<FamilyHistoryDTO> getFamilyHistory(@PathVariable Long patientId) {

		return historyService.getFamilyHistory(patientId);
	}

	// Add Family History
	@PostMapping("/family")
	public FamilyHistoryDTO addFamilyHistory(@RequestBody FamilyHistoryDTO dto) {

		return historyService.addFamilyHistory(dto);
	}

	// Delete Family History
	@DeleteMapping("/family/{id}")
	public void deleteFamilyHistory(@PathVariable Long id) {

		historyService.deleteFamilyHistory(id);
	}

	@GetMapping("/patient/{patientId}")
	public HistoryResponseDTO getPatientHistory(@PathVariable Long patientId) {

		return historyService.getPatientHistory(patientId);
	}
	
	@GetMapping("/master-data")
	public HistoryMasterDataDTO getHistoryMasterData() {
	    return historyService.getHistoryMasterData();
	}
}