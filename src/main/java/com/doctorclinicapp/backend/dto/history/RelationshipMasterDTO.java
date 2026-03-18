package com.doctorclinicapp.backend.dto.history;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelationshipMasterDTO {

    private Long id;
    private String relationshipName;

}