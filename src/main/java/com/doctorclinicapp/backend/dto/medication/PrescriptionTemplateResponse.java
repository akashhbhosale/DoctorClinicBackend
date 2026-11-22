
package com.doctorclinicapp.backend.dto.medication;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrescriptionTemplateResponse {

    private Long id;

    private String templateName;
    private String description;

    private Long doctorId;
    private String doctorName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<PrescriptionTemplateMedicineResponse> medicines;
}
