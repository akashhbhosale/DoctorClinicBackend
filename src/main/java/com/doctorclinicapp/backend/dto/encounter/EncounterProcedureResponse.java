package com.doctorclinicapp.backend.dto.encounter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EncounterProcedureResponse {

    private Long id;

    private Long procedureId;
    private String procedureName;

    private String site;
    private String device;
    private String method;

    private String laterality;
    private String priority;
}