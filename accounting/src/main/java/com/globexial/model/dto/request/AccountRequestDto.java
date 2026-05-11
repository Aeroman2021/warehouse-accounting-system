package com.globexial.model.dto.request;

import com.globexial.model.enums.AccountType;

public record AccountRequestDto(
        String code,
        String name,
        AccountType accountType,
        boolean active
) {
}
