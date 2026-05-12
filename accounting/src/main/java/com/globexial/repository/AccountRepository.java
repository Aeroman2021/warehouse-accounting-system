package com.globexial.repository;

import com.globexial.model.dto.response.AccountResponseDto;
import com.globexial.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByCode(String code);
}
