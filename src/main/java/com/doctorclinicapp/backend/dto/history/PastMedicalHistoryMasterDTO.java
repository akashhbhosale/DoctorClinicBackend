package com.doctorclinicapp.backend.dto.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PastMedicalHistoryMasterDTO {

    private Long id;
    private String historyName;

}