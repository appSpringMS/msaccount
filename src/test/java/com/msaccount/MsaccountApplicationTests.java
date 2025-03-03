package com.msaccount;

import com.msaccount.models.dto.AccountResponse;
import com.msaccount.models.entities.Account;
import com.msaccount.models.enums.AccountType;
import com.msaccount.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class MsaccountApplicationTests extends AbstractIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setup() {
        accountRepository.deleteAll();
    }

    @Test
    void createUpdateGetAccountsTest() {
        Account account = Account.builder()
                .accountNumber("123")
                .balance(44.00)
                .accountType(AccountType.SAVINGS)
                .build();
        accountRepository.save(account);

        Account accountUpdate = Account.builder()
                .accountNumber("123")
                .balance(55.00)
                .accountType(AccountType.SAVINGS)
                .build();

        String accountResource = "/api/accounts/";
        String url = "/api/accounts/{accountNumber}";


        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<Account> requestEntity = new HttpEntity<>(accountUpdate, headers);

        ResponseEntity<AccountResponse> resultUpdate = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, AccountResponse.class, account.getAccountNumber());

        AccountResponse result = testRestTemplate.getForObject(accountResource + account.getAccountNumber(), AccountResponse.class);

        assertThat(result.getBalance()).as("value is set").isEqualTo(55);

    }
}
