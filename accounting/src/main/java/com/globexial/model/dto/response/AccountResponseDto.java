package com.globexial.model.dto.response;

import com.globexial.model.enums.AccountType;

public record AccountResponseDto(
        Long id,
        String code,
        String name,
        boolean active,
        AccountType accountType
) {
}
