package com.msaccount.utils;

public class AccountNotFoundException extends RuntimeException {
       public AccountNotFoundException(String message) {
           super(message);
       }
}
