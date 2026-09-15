package com.abcbank.api.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@Validated
@Tag(name = "Accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountNumber}")
    @Operation(summary = "Retrieve account information", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account returned"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Authentication failure"),
            @ApiResponse(responseCode = "403", description = "Account access denied"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    public ResponseEntity<AccountInformation> getAccount(
            @Parameter(description = "Customer bank account number", required = true, in = ParameterIn.PATH)
            @PathVariable @NotBlank @Pattern(regexp = "\\d{12}") String accountNumber,
            @Parameter(description = "Customer/user identifier", required = true, in = ParameterIn.QUERY)
            @RequestParam @NotBlank String userId,
            @Parameter(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization) {
        if (!"Bearer valid-token".equals(authorization)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(accountService.getAccount(accountNumber, userId));
    }
}