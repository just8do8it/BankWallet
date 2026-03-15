package com.wallet.bankwallet.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferRequest {
    @NotNull
    @Positive
    private Long sourceId;

    @NotNull
    @Positive
    private Long destinationId;

    @NotNull
    @Positive
    private BigDecimal amount;
}
