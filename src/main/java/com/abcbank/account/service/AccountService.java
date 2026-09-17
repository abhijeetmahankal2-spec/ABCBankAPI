package com.abcbank.account.service;

import com.abcbank.account.model.AccountResponse;
import com.abcbank.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class AccountService {

    private static final Pattern ACCOUNT_NUMBER = Pattern.compile("\\d{12}");
    private static final Pattern USER_ID = Pattern.compile("USR\\d{6}");
    private static final String VALID_TOKEN = "Bearer valid-token";

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountResponse getAccount(String accountNumber, String userId, String authorizationHeader) {
        if (!VALID_TOKEN.equals(authorizationHeader)) {
            throw new UnauthenticatedException();
        }
        if (!ACCOUNT_NUMBER.matcher(accountNumber).matches()
            || (userId != null && !USER_ID.matcher(userId).matches())) {
            throw new InvalidRequestException();
        }

        AccountResponse account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(AccountNotFoundException::new);
        if (userId != null && !account.userId().equals(userId)) {
            throw new ForbiddenException();
        }
        return account;
    }
}