package com.doctorclinicapp.backend.service.encounter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.doctorclinicapp.backend.dto.encounter.*;
import com.doctorclinicapp.backend.enums.*;
import com.doctorclinicapp.backend.exception.ResourceNotFoundException;
import com.doctorclinicapp.backend.model.encounter.*;
import com.doctorclinicapp.backend.model.procedure.*;
import com.doctorclinicapp.backend.repository.encounter.*;
import com.doctorclinicapp.backend.repository.procedure.*;

@Service
@RequiredArgsConstructor
public class EncounterProcedureService {

    private final EncounterProcedureRepository repository;
    private final EncounterRepository encounterRepo;
    private final ProcedureMasterRepository procedureRepo;
    private final ProcedureSiteRepository siteRepo;
    private final ProcedureDeviceRepository deviceRepo;
    private final ProcedureMethodRepository methodRepo;

    // 🔥 ADD PROCEDURE
    public EncounterProcedureResponse addProcedure(AddEncounterProcedureRequest req) {

        Encounter encounter = encounterRepo.findById(req.getEncounterId())
                .orElseThrow(() -> new ResourceNotFoundException("Encounter", "id", req.getEncounterId()));

        ProcedureMaster procedure = procedureRepo.findById(req.getProcedureId())
                .orElseThrow(() -> new ResourceNotFoundException("Procedure", "id", req.getProcedureId()));

        EncounterProcedure entity = new EncounterProcedure();
        entity.setEncounter(encounter);
        entity.setProcedure(procedure);

        // OPTIONAL FIELDS
        if (req.getSiteId() != null) {
            entity.setSite(siteRepo.findById(req.getSiteId()).orElse(null));
        }

        if (req.getDeviceId() != null) {
            entity.setDevice(deviceRepo.findById(req.getDeviceId()).orElse(null));
        }

        if (req.getMethodId() != null) {
            entity.setMethod(methodRepo.findById(req.getMethodId()).orElse(null));
        }

        // ENUMS
        if (req.getLaterality() != null) {
        	entity.setLaterality(req.getLaterality());
        }

        if (req.getPriority() != null) {
        	entity.setPriority(req.getPriority());
        }

        EncounterProcedure saved = repository.save(entity);

        return mapToResponse(saved);
    }

    // 🔥 GET BY ENCOUNTER
    public List<EncounterProcedureResponse> getByEncounter(Long encounterId) {

        return repository.findByEncounterId(encounterId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // DELETE
    public void deleteProcedure(Long id) {

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("EncounterProcedure", "id", id);
        }

        repository.deleteById(id);
    }
    
    private EncounterProcedureResponse mapToResponse(EncounterProcedure e) {
        return EncounterProcedureResponse.builder()
                .id(e.getId())
                .procedureId(e.getProcedure().getId())
                .procedureName(e.getProcedure().getName())
                .site(e.getSite() != null ? e.getSite().getName() : null)
                .device(e.getDevice() != null ? e.getDevice().getName() : null)
                .method(e.getMethod() != null ? e.getMethod().getName() : null)
                .laterality(e.getLaterality() != null ? e.getLaterality().name() : null)
                .priority(e.getPriority() != null ? e.getPriority().name() : null)
                .build();
    }
}