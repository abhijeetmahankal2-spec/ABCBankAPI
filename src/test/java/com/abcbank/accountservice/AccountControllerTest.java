package com.abcbank.accountservice;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void returnsAccountForAuthenticatedOwner() throws Exception {
        mockMvc.perform(get("/api/v1/accounts/1234567890")
                        .param("userId", "user-1001")
                        .with(httpBasic("api-user", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
                .andExpect(jsonPath("$.balance").value(1250.75));
    }

    @Test
    void rejectsUnauthenticatedRequest() throws Exception {
        mockMvc.perform(get("/api/v1/accounts/1234567890").param("userId", "user-1001"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void rejectsAccessForAnotherUser() throws Exception {
        mockMvc.perform(get("/api/v1/accounts/1234567890")
                        .param("userId", "user-2002")
                        .with(httpBasic("api-user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void returnsNotFoundForUnknownAccount() throws Exception {
        mockMvc.perform(get("/api/v1/accounts/0000000000")
                        .param("userId", "user-1001")
                        .with(httpBasic("api-user", "password")))
                .andExpect(status().isNotFound());
    }

    @Test
    void rejectsMissingUserId() throws Exception {
        mockMvc.perform(get("/api/v1/accounts/1234567890")
                        .with(httpBasic("api-user", "password")))
                .andExpect(status().isBadRequest());
    }
}
