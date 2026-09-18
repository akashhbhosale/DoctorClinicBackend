package com.doctorclinicapp.backend.dto.medication;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteOfAdministrationMasterDTO {
    private Long id;
    private String routeName;
}
