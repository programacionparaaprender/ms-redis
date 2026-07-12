package com.programacionpa.app.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.programacionpa.app.redis.models.AccountBalance;
import com.programacionpa.app.services.AccountBalanceService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/account-balances")
@AllArgsConstructor
public class AccountBalanceController {
    private final AccountBalanceService accountBalanceService;

    @GetMapping("/{accountId}")
    public Mono<AccountBalance> getAccountBalance(@PathVariable String accountId) {
        return accountBalanceService.findByAccountId(accountId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AccountBalance> saveAccountBalance(@RequestBody AccountBalance accountBalance) {
        return accountBalanceService.save(accountBalance);
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAccountBalance(@PathVariable String accountId) {
        return accountBalanceService.deleteByAccountId(accountId);
    }
}
