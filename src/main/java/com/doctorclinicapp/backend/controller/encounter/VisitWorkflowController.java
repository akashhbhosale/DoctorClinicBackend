package com.doctorclinicapp.backend.controller.encounter;

import com.doctorclinicapp.backend.service.encounter.VisitWorkflow;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class VisitWorkflowController {
    private final VisitWorkflow workflow;

    @GetMapping("/patient/{patientId}/active")
    public Map<String, Long> active(@PathVariable Long patientId, Principal principal) {
        return java.util.Collections.singletonMap("id", workflow.active(patientId, principal.getName()));
    }

    @PostMapping("/patient/{patientId}/start")
    public Map<String, Long> start(@PathVariable Long patientId, Principal principal) {
        return Map.of("id", workflow.start(patientId, principal.getName()));
    }

    @PutMapping("/patient/{patientId}/{id}/reopen")
    public void reopen(@PathVariable Long patientId, @PathVariable Long id, Principal principal) {
        workflow.reopen(patientId, id, principal.getName());
    }

    @PutMapping("/{id}/complete")
    public void complete(@PathVariable Long id, Principal principal) {
        workflow.finish(id, principal.getName());
    }

    @GetMapping("/{id}/summary")
    public JsonNode summary(@PathVariable Long id, Principal principal) {
        return workflow.summary(id, principal.getName());
    }
}
