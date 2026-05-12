package com.globexial.service.impl;

import com.globexial.excception.ResourceNotFoundException;
import com.globexial.model.dto.request.AccountRequestDto;
import com.globexial.model.dto.response.AccountResponseDto;
import com.globexial.model.entity.Account;
import com.globexial.repository.AccountRepository;
import com.globexial.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public AccountResponseDto save(AccountRequestDto requestDto) {
        var account = new Account(null, requestDto.code(), requestDto.name(), requestDto.active(),
                requestDto.accountType());
        var saveAccount = accountRepository.save(account);
        return new AccountResponseDto(saveAccount.getId(), saveAccount.getCode(),
                saveAccount.getName(), saveAccount.isActive(), saveAccount.getAccountType());
    }

    @Override
    public AccountResponseDto findById(Long id) {
        var account = accountRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("%s with id %d not found".formatted("product", id)));

        return new AccountResponseDto(account.getId(), account.getCode(),
                account.getName(), account.isActive(), account.getAccountType());

    }

    @Override
    public Page<AccountResponseDto> findAll(String pageNumber, String pageSize) {
        var pageable = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(pageSize));
        var products =
                accountRepository.findAll(pageable)
                        .stream()
                        .map(account -> new AccountResponseDto(account.getId(), account.getCode(),
                                account.getName(), account.isActive(), account.getAccountType()))
                        .toList();
        return new PageImpl<>(products, pageable, products.size());
    }

    @Override
    public Account findByCode(String code) {
        return accountRepository.findByCode(code).orElseThrow(()->
                new ResourceNotFoundException("%s with id %s not found".formatted("account", code)));
    }
}
