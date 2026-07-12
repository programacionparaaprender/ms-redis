package com.programacionpa.app.redis.models;

import java.math.BigDecimal;

public record AccountBalance(
        String accountId,
        BigDecimal balance,
        String currency,
        String accountType
) {
}
