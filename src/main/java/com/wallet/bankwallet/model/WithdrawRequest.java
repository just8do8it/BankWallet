package com.wallet.bankwallet.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawRequest {
    private Long walletId;
    private BigDecimal amount;
}
