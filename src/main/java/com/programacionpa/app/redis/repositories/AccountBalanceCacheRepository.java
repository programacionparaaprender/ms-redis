package com.programacionpa.app.redis.repositories;


import com.programacionpa.app.redis.models.AccountBalance;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
public class AccountBalanceCacheRepository {

    private final ReactiveRedisTemplate<String, AccountBalance> redisTemplate;
    private final Duration ttl;
    private final String keyPrefix;

    public AccountBalanceCacheRepository(
            ReactiveRedisTemplate<String, AccountBalance> redisTemplate,
            @Value("${account-balance.cache.ttl}") Duration ttl,
            @Value("${account-balance.cache.key-prefix}") String keyPrefix
    ) {
        this.redisTemplate = redisTemplate;
        this.ttl = ttl;
        this.keyPrefix = keyPrefix;
    }

    public Mono<AccountBalance> findByAccountId(String accountId) {
        String key = buildKey(accountId);

        return redisTemplate
                .opsForValue()
                .get(key)
                .doOnNext(value -> log.info("CACHE HIT. key={}", key))
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("CACHE MISS. key={}", key);
                    return Mono.empty();
                }));
    }

    public Mono<AccountBalance> save(AccountBalance accountBalance) {
        String key = buildKey(accountBalance.accountId());

        return redisTemplate
                .opsForValue()
                .set(key, accountBalance, ttl)
                .flatMap(saved -> {
                    if (Boolean.TRUE.equals(saved)) {
                        log.info("Saldo guardado en Redis. key={}, ttl={}", key, ttl);
                        return Mono.just(accountBalance);
                    }

                    return Mono.error(new IllegalStateException(
                            "Redis no confirmó el guardado de la clave " + key
                    ));
                });
    }

    private String buildKey(String accountId) {
        return keyPrefix + accountId;
    }

    public Mono<Void> deleteByAccountId(String accountId) {
        String key = buildKey(accountId);
        return redisTemplate.opsForValue().delete(key).then();
    }
}
