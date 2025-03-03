package com.msaccount.models.dto;

import com.msaccount.models.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private String accountNumber;
    private AccountType accountType;
    private Double balance;
    private Long customerId;
}
