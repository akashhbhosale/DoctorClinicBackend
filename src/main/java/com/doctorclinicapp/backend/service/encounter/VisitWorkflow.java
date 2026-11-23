package com.doctorclinicapp.backend.service.encounter;

import com.doctorclinicapp.backend.model.encounter.Encounter;
import com.doctorclinicapp.backend.model.Patient;
import com.doctorclinicapp.backend.model.Doctor;
import com.doctorclinicapp.backend.repository.DoctorRepository;
import com.doctorclinicapp.backend.repository.assessment.EncounterVitalsRepository;
import com.doctorclinicapp.backend.service.allergy.AllergyService;
import com.doctorclinicapp.backend.service.history.HistoryService;
import com.doctorclinicapp.backend.service.assessment.*;
import com.doctorclinicapp.backend.service.medication.*;
import com.doctorclinicapp.backend.service.nursing.NursingAssessmentRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class VisitWorkflow {
    private final EntityManager em;
    private final ObjectMapper json;
    private final DoctorRepository doctors;
    private final EncounterService encounters;
    private final AllergyService allergies;
    private final HistoryService history;
    private final EncounterVitalsService vitals;
    private final EncounterVitalsRepository vitalRecords;
    private final EncounterLaboratoryService labs;
    private final EncounterRadiologyService radiology;
    private final EncounterObservationService observations;
    private final EncounterMedicationService medicines;
    private final EncounterSurgicalTreatmentService surgeries;
    private final EncounterFollowUpService followUp;
    private final NursingAssessmentRecordService nursing;

    public Long active(Long patientId, String username) {
        return em.createQuery("select e.id from Encounter e where e.patient.id = :patient and e.doctor.username = :doctor and e.visitStatus = 'IN_PROGRESS' and e.id not in (select a.encounterId from EncounterArchive a)", Long.class)
            .setParameter("patient", patientId).setParameter("doctor", username)
            .getResultStream().findFirst().orElse(null);
    }

    public Long start(Long patientId, String username) {
        if (em.find(Patient.class, patientId, LockModeType.PESSIMISTIC_WRITE) == null)
            throw new ResponseStatusException(NOT_FOUND);
        Long existing = active(patientId, username);
        if (existing != null) return existing;
        Doctor doctor = doctors.findByUsername(username).orElseThrow(() -> new ResponseStatusException(FORBIDDEN));
        Encounter visit = encounters.createEncounter(patientId, doctor.getId());
        visit.setVisitStatus("IN_PROGRESS");
        return visit.getId();
    }

    private Encounter owned(Long id, String username, LockModeType lock) {
        Encounter visit = em.find(Encounter.class, id, lock);
        if (visit == null || em.find(com.doctorclinicapp.backend.model.encounter.EncounterArchive.class, id) != null || !visit.getDoctor().getUsername().equals(username))
            throw new ResponseStatusException(NOT_FOUND);
        return visit;
    }

    public void reopen(Long patientId, Long id, String username) {
        if (em.find(Patient.class, patientId, LockModeType.PESSIMISTIC_WRITE) == null)
            throw new ResponseStatusException(NOT_FOUND);
        Encounter visit = owned(id, username, LockModeType.PESSIMISTIC_WRITE);
        if (!patientId.equals(visit.getPatient().getId())) throw new ResponseStatusException(NOT_FOUND);
        if ("COMPLETED".equals(visit.getVisitStatus()))
            throw new ResponseStatusException(CONFLICT, "Completed encounters are read-only");
        Long openId = active(patientId, username);
        if (openId != null && !id.equals(openId)) throw new ResponseStatusException(CONFLICT);
        visit.setVisitStatus("IN_PROGRESS");
    }

    public void finish(Long id, String username) {
        Encounter visit = owned(id, username, LockModeType.PESSIMISTIC_WRITE);
        if ("COMPLETED".equals(visit.getVisitStatus())) return;
        if (!"IN_PROGRESS".equals(visit.getVisitStatus())) throw new ResponseStatusException(CONFLICT);
        visit.setFinalRecord(buildRecord(visit).toString());
        visit.setVisitStatus("COMPLETED");
    }

    public JsonNode summary(Long id, String username) {
        Encounter visit = owned(id, username, LockModeType.NONE);
        if ("IN_PROGRESS".equals(visit.getVisitStatus()) || visit.getFinalRecord() == null) return buildRecord(visit);
        try { return json.readTree(visit.getFinalRecord()); }
        catch (com.fasterxml.jackson.core.JsonProcessingException e) { throw new IllegalStateException("Cannot read saved encounter", e); }
    }

    private ObjectNode buildRecord(Encounter visit) {
        Long id = visit.getId();
        Long patientId = visit.getPatient().getId();
        ObjectNode record = json.createObjectNode();
        // Serialize the actual entities instead of Hibernate proxy internals.
        record.set("patient", json.valueToTree(org.hibernate.Hibernate.unproxy(visit.getPatient())));
        record.set("doctor", json.valueToTree(org.hibernate.Hibernate.unproxy(visit.getDoctor())));
        record.set("visit", json.valueToTree(encounters.getEncounterDetails(id)));
        record.set("medicines", json.valueToTree(medicines.getByEncounter(id)));
        record.set("surgeries", json.valueToTree(surgeries.getByEncounter(id)));
        record.set("followUp", json.valueToTree(followUp.getByEncounter(id)));
        ObjectNode summary = record.putObject("summary");
        summary.set("vitals", json.valueToTree(vitalRecords.findByEncounterId(id).isPresent() ? vitals.getVitalsByEncounterId(id) : null));
        summary.set("labs", json.valueToTree(labs.getLaboratoryByEncounterId(id)));
        summary.set("radiology", json.valueToTree(radiology.getRadiologyByEncounterId(id)));
        summary.set("observations", json.valueToTree(observations.getObservationsByEncounterId(id)));
        summary.set("nursing", json.valueToTree(nursing.getByEncounter(id)));
        summary.set("allergies", json.valueToTree(allergies.getAllergiesByPatient(patientId)));
        summary.set("history", json.valueToTree(history.getPatientHistory(patientId)));
        return record;
    }
}
