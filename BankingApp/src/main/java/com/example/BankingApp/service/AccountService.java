package com.example.BankingApp.service;

import java.util.List;
import com.example.BankingApp.dto.AccountDto;
import com.example.BankingApp.entity.Account;

public interface AccountService {
    AccountDto createAccount(Account account);
    AccountDto getAccountById(Long id);
    AccountDto deposit(Long id, double amount);
    AccountDto withdraw(Long id, double amount);
    List<AccountDto> getAllAccounts();
    void deleteAccount(Long id);
}
