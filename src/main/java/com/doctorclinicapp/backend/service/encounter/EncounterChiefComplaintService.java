package com.doctorclinicapp.backend.service.encounter;

import java.util.List;

import org.springframework.stereotype.Service;

import com.doctorclinicapp.backend.dto.encounter.AddEncounterChiefComplaintRequest;
import com.doctorclinicapp.backend.dto.encounter.EncounterChiefComplaintResponse;
import com.doctorclinicapp.backend.exception.ResourceNotFoundException;
import com.doctorclinicapp.backend.model.encounter.ChiefComplaint;
import com.doctorclinicapp.backend.model.encounter.Encounter;
import com.doctorclinicapp.backend.model.encounter.EncounterChiefComplaint;
import com.doctorclinicapp.backend.repository.encounter.ChiefComplaintRepository;
import com.doctorclinicapp.backend.repository.encounter.EncounterChiefComplaintRepository;
import com.doctorclinicapp.backend.repository.encounter.EncounterRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncounterChiefComplaintService {

    private final EncounterChiefComplaintRepository encounterChiefComplaintRepository;
    private final EncounterRepository encounterRepository;
    private final ChiefComplaintRepository chiefComplaintRepository;

    public EncounterChiefComplaint addComplaint(AddEncounterChiefComplaintRequest request) {

        Encounter encounter = encounterRepository.findById(request.getEncounterId())
                .orElseThrow(() -> new ResourceNotFoundException("Encounter not found"));

        ChiefComplaint complaint = chiefComplaintRepository.findById(request.getComplaintId())
                .orElseThrow(() -> new ResourceNotFoundException("Chief complaint not found"));

        EncounterChiefComplaint encounterChiefComplaint = EncounterChiefComplaint.builder()
                .encounter(encounter)
                .chiefComplaint(complaint)
                .timeSinceDays(request.getTimeSinceDays())
                .build();

        return encounterChiefComplaintRepository.save(encounterChiefComplaint);
    }
    
    public List<EncounterChiefComplaintResponse> getComplaintsByEncounterId(Long encounterId) {

        List<EncounterChiefComplaint> complaints =
                encounterChiefComplaintRepository.findByEncounterId(encounterId);

        return complaints.stream()
                .map(c -> new EncounterChiefComplaintResponse(
                        c.getChiefComplaint().getComplaintName(),
                        c.getTimeSinceDays()
                ))
                .toList();
    }
    
    public void deleteComplaint(Long id) {

        EncounterChiefComplaint complaint =
                encounterChiefComplaintRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));

        encounterChiefComplaintRepository.delete(complaint);
    }
}