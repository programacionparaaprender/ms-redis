package com.programacionpa.app.services;

import org.springframework.stereotype.Service;

import com.programacionpa.app.redis.models.AccountBalance;
import com.programacionpa.app.redis.repositories.AccountBalanceCacheRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AccountBalanceService {
    private final AccountBalanceCacheRepository accountBalanceCacheRepository;

    public Mono<AccountBalance> findByAccountId(String accountId) {
        return accountBalanceCacheRepository.findByAccountId(accountId);
    }

    public Mono<AccountBalance> save(AccountBalance accountBalance) {
        return accountBalanceCacheRepository.save(accountBalance);
    }

    public Mono<Void> deleteByAccountId(String accountId) {
        return accountBalanceCacheRepository.deleteByAccountId(accountId);
    }
}
