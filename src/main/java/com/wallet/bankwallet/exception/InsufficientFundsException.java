package com.wallet.bankwallet.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(Long id) {
        super("Insufficient funds in wallet with id: " + id);
    }
}
