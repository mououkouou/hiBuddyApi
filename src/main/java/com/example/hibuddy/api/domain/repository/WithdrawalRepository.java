package com.example.hibuddy.api.domain.repository;

import com.example.hibuddy.api.domain.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
    Optional<Withdrawal> findByEmail(String email);
}
