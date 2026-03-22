package com.doctorclinicapp.backend.dto.procedure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureMasterResponse {
    private Long id;
    private String code;
    private String name;
}