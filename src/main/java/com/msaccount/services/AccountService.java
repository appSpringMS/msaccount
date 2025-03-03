package com.msaccount.services;

import com.msaccount.models.dto.AccountRequest;
import com.msaccount.models.dto.AccountResponse;
import com.msaccount.models.dto.BaseResponse;
import com.msaccount.models.entities.Account;
import com.msaccount.repositories.AccountRepository;
import com.msaccount.utils.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final WebClient.Builder webClientBuilder;

    public AccountResponse addAccount(AccountRequest accountRequest) {
        log.info("Loading data...");

        String baseUrl = "lb://mscustomer/api/customers/exists/"+accountRequest.getCustomerId();
        log.info(baseUrl);
        BaseResponse result = this.webClientBuilder.build()
                .get()
                .uri(baseUrl)
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();
        if (result != null && !result.hasErrors()) {
            var account = Account.builder()
                    .accountNumber(accountRequest.getAccountNumber())
                    .accountType(accountRequest.getAccountType())
                    .customerId(accountRequest.getCustomerId())
                    .balance(accountRequest.getBalance())
                    .build();

            Account savedAccount = accountRepository.save(account);
            return mapToAccountResponse(savedAccount);
        } else {
            log.info("Loading data error...");
            throw new IllegalArgumentException("Customer not exist");
        }
    }

    public List<AccountResponse> getAllAccounts() {
        var accounts = accountRepository.findAll();
        return accounts.stream().map(this::mapToAccountResponse).toList();
    }

    public AccountResponse getAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with account number: " + accountNumber));
        ;
        return mapToAccountResponse(account);
    }

    public AccountResponse updateAccount(String accountNumber, AccountRequest accountRequest) {
        Account existingAccount = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found with account number: " + accountNumber));
        existingAccount.setAccountNumber(accountRequest.getAccountNumber());
        existingAccount.setAccountType(accountRequest.getAccountType());
        existingAccount.setCustomerId(accountRequest.getCustomerId());
        existingAccount.setBalance(accountRequest.getBalance());
        Account savedAccount = accountRepository.save(existingAccount);
        return mapToAccountResponse(savedAccount);
    }

    private AccountResponse mapToAccountResponse(Account account) {
        return AccountResponse.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .customerId(account.getCustomerId())
                .balance(account.getBalance())
                .build();
    }

}
