package com.msaccount.models.dto;

import com.msaccount.models.enums.AccountType;
import lombok.Data;

@Data
public class AccountRequest {
    private String accountNumber;
    private AccountType accountType;
    private Double balance;
    private Long customerId;
}
