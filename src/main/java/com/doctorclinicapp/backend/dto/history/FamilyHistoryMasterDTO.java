package com.doctorclinicapp.backend.dto.history;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyHistoryMasterDTO {

    private Long id;
    private String historyName;

}