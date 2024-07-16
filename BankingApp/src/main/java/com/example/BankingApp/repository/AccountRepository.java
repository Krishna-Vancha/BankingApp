package com.example.BankingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BankingApp.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
