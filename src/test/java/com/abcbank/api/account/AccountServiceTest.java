package com.abcbank.api.account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountServiceTest {

    private final AccountService accountService = new AccountService();

    @Test
    void deniesAccessToAnotherUsersAccount() {
        assertThrows(AccountAccessDeniedException.class,
                () -> accountService.getAccount("123456789012", "OTHER-USER"));
    }

    @Test
    void reportsUnknownAccount() {
        assertThrows(AccountNotFoundException.class,
                () -> accountService.getAccount("999999999999", "USR100245"));
    }
}