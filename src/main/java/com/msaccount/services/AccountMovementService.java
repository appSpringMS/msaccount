package com.msaccount.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.msaccount.models.dto.AccountMovementRequest;
import com.msaccount.models.dto.AccountMovementResponse;
import com.msaccount.models.entities.Account;
import com.msaccount.models.entities.AccountMovement;
import com.msaccount.repositories.AccountMovementRepository;
import com.msaccount.repositories.AccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountMovementService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final AccountMovementRepository accountMovementRepository;
    private final AccountRepository accountRepository;

    public AccountMovementResponse addAccountMovement(AccountMovementRequest accountMovementRequest) {
        System.out.println(accountMovementRequest.getAccountNumber());
        String accountNumber = accountMovementRequest.getAccountNumber();
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);

        if (optionalAccount.isPresent()) {
            Double balance = getTotalBalance(accountMovementRequest, optionalAccount);
            AccountMovement accountMovement = AccountMovement.builder()
                    .movementType(accountMovementRequest.getMovementType())
                    .amount(accountMovementRequest.getAmount())
                    .balance(balance)
                    .account(optionalAccount.get())
                    .build();

            Account account = optionalAccount.get();
            account.setBalance(balance);
            accountRepository.save(account);

            AccountMovement savedAccountMovement = accountMovementRepository.save(accountMovement);
            return mapToAccountMovementResponse(savedAccountMovement);
        } else {
            throw new IllegalArgumentException("Account not found with ID: " + accountMovementRequest.getAccountNumber());
        }
    }

    private static Double getTotalBalance(AccountMovementRequest accountMovementRequest, Optional<Account> optionalAccount) {
        if (optionalAccount.isPresent()) {
            Double balance = optionalAccount.get().getBalance();
            if (accountMovementRequest.getMovementType().name().equals("DEBIT")) {
                if (balance >= accountMovementRequest.getAmount()) {
                    balance = balance - accountMovementRequest.getAmount();
                } else {
                    throw new IllegalArgumentException("Balance not available");
                }
            } else {
                balance = balance + accountMovementRequest.getAmount();
            }
            return balance;
        } else {
            throw new IllegalArgumentException("Account not found with ID: " + accountMovementRequest.getAccountNumber());
        }
    }

    public List<AccountMovementResponse> getAllAccountMovements() {
        var accounts = accountMovementRepository.findAll();
        return accounts.stream().map(this::mapToAccountMovementResponse).toList();
    }

    public List<AccountMovementResponse> getReport(Long customerId, String startDate, String endDate) {
        String accountNumber = "12345678P";
        LocalDate startDateTime = parseDate(startDate);
        LocalDate endDateTime = parseDate(endDate);
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);

        if (optionalAccount.isPresent()) {
            List<AccountMovement> accountMovements = accountMovementRepository.findAllByTransactionDateBetweenAndAccount(startDateTime, endDateTime, optionalAccount.get());
            return accountMovements.stream()
                    .map(this::mapToAccountMovementResponse).toList();

        } else {
            throw new IllegalArgumentException("Account not found with ID: " + accountNumber);
        }
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, DATE_FORMATTER);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format: " + date);
        }
    }

    private AccountMovementResponse mapToAccountMovementResponse(AccountMovement accountMovement) {
        return AccountMovementResponse.builder()
                .account_movement_id(accountMovement.getAccount_movement_id())
                .accountNumber(accountMovement.getAccount().getAccountNumber())
                .accountType(accountMovement.getAccount().getAccountType())
                .initialBalance(accountMovement.getAccount().getBalance())
                .customerId(accountMovement.getAccount().getCustomerId())
                .amount(accountMovement.getAmount())
                .balance(accountMovement.getBalance())
                .movementType(accountMovement.getMovementType())
                .transactionDate(accountMovement.getTransactionDate())
                .build();
    }
}
