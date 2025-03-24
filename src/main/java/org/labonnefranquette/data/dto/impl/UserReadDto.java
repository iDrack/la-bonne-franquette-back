package org.labonnefranquette.data.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReadDto {
    private String username;

    private Long restaurantId;

    private String roles;
}
