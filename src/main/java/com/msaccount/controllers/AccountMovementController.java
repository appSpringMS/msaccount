package com.msaccount.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.msaccount.models.dto.AccountMovementRequest;
import com.msaccount.models.dto.AccountMovementResponse;
import com.msaccount.services.AccountMovementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/account-movements")
@RequiredArgsConstructor
public class AccountMovementController {
    private final AccountMovementService accountMovementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountMovementResponse addAccount(@RequestBody AccountMovementRequest accountMovementRequest) {
        return this.accountMovementService.addAccountMovement(accountMovementRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccountMovementResponse> getAllAccounts() {
        return this.accountMovementService.getAllAccountMovements();
    }

    @GetMapping("/report/{customerId}")
    public List<AccountMovementResponse> getReportes(
            @PathVariable("customerId") Long customerId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        return this.accountMovementService.getReport(customerId, startDate, endDate);
    }
}
