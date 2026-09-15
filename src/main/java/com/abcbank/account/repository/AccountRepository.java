package com.abcbank.account.repository;

import com.abcbank.account.model.AccountResponse;

import java.util.Optional;

public interface AccountRepository {

    Optional<AccountResponse> findByAccountNumber(String accountNumber);
}