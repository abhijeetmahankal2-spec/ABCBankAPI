package com.abcbank.api.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @Test
    void returnsAccountForAuthenticatedOwner() throws Exception {
        when(accountService.getAccount("123456789012", "USR100245")).thenReturn(
                new AccountInformation("123456789012", "USR100245", "John Doe", "SAVINGS", "INR",
                        new BigDecimal("125000.50"), "ACTIVE", "NAG001", "BANK0001234"));

        mockMvc.perform(get("/api/v1/accounts/123456789012")
                        .param("userId", "USR100245")
                        .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("123456789012"))
                .andExpect(jsonPath("$.availableBalance").value(125000.50));
    }

    @Test
    void rejectsMissingAuthentication() throws Exception {
        mockMvc.perform(get("/api/v1/accounts/123456789012").param("userId", "USR100245"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void rejectsInvalidAccountNumber() throws Exception {
        mockMvc.perform(get("/api/v1/accounts/not-an-account")
                        .param("userId", "USR100245")
                        .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isBadRequest());
    }
}