package com.example.BankingApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.example.BankingApp.dto.AccountDto;
import com.example.BankingApp.entity.Account;
import com.example.BankingApp.mapper.AccountMapper;
import com.example.BankingApp.repository.AccountRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account account;
    private AccountDto accountDto;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setId(1L);
        account.setAccountHolderName("John Doe");
        account.setBalance(1000.0);

        accountDto = AccountMapper.mapToAccountDto(account);
    }

    @Test
    void testCreateAccount() {
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDto createdAccountDto = accountService.createAccount(account);

        assertEquals(accountDto.getId(), createdAccountDto.getId());
        assertEquals(accountDto.getAccountHolderName(), createdAccountDto.getAccountHolderName());
        assertEquals(accountDto.getBalance(), createdAccountDto.getBalance());

        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testGetAccountById() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        AccountDto foundAccountDto = accountService.getAccountById(1L);

        assertEquals(accountDto.getId(), foundAccountDto.getId());
        assertEquals(accountDto.getAccountHolderName(), foundAccountDto.getAccountHolderName());
        assertEquals(accountDto.getBalance(), foundAccountDto.getBalance());

        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void testDeposit() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDto updatedAccountDto = accountService.deposit(1L, 500.0);

        assertEquals(1500.0, updatedAccountDto.getBalance());

        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testWithdraw() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountDto updatedAccountDto = accountService.withdraw(1L, 500.0);

        assertEquals(500.0, updatedAccountDto.getBalance());

        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testWithdrawInsufficientFunds() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        assertThrows(RuntimeException.class, () -> accountService.withdraw(1L, 1500.0));

        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, never()).save(any(Account.class));
    }
}
