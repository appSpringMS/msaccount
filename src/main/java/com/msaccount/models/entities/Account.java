package com.msaccount.models.entities;

import com.msaccount.models.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class Account {

    @Id
    private String accountNumber;

    private AccountType accountType;

    private Double balance;

    private Long customerId;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountMovement> movements;

}
