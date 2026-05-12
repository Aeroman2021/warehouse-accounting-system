package com.globexial.service;

import com.globexial.model.dto.request.AccountRequestDto;
import com.globexial.model.dto.response.AccountResponseDto;
import com.globexial.model.entity.Account;
import com.globexial.service.base.BaseCrud;

public interface AccountService extends BaseCrud<AccountResponseDto, AccountRequestDto> {
    Account findByCode(String code);

}
