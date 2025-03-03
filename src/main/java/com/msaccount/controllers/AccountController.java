package com.msaccount.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.msaccount.models.dto.AccountRequest;
import com.msaccount.models.dto.AccountResponse;
import com.msaccount.services.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse addAccount(@RequestBody AccountRequest accountRequest) {
        return this.accountService.addAccount(accountRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponse> getAllAccounts() {
        return this.accountService.getAllAccounts();
    }

    @GetMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse getAccount(@PathVariable("accountNumber") String accountNumber) {
        return this.accountService.getAccount(accountNumber);
    }

    @PutMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse updateAccount(
            @PathVariable String accountNumber,
            @RequestBody AccountRequest accountRequest) {
        return this.accountService.updateAccount(accountNumber, accountRequest);
    }
}
