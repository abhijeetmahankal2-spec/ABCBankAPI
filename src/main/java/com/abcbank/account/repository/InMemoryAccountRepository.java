package com.abcbank.account.repository;

import com.abcbank.account.model.AccountResponse;
import com.abcbank.account.model.AccountStatus;
import com.abcbank.account.model.AccountType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryAccountRepository implements AccountRepository {

    private final Map<String, AccountResponse> accounts = Map.of(
            "123456789012",
            new AccountResponse(
                    "123456789012",
                    "USR100245",
                    "John Doe",
                    AccountType.SAVINGS,
                    "INR",
                    new BigDecimal("125000.50"),
                    AccountStatus.ACTIVE,
                    "NAG001",
                    "BANK0001234"));

    @Override
    public Optional<AccountResponse> findByAccountNumber(String accountNumber) {
        return Optional.ofNullable(accounts.get(accountNumber));
    }
}