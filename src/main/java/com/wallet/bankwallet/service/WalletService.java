package com.wallet.bankwallet.service;

import com.wallet.bankwallet.model.Wallet;
import com.wallet.bankwallet.exception.InsufficientFundsException;
import com.wallet.bankwallet.exception.UserNotFoundException;
import com.wallet.bankwallet.exception.WalletNotFoundException;
import com.wallet.bankwallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;

    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    public Wallet findById(Long id) throws WalletNotFoundException {
        return walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException(id));
    }

    public Wallet findByUserId(Long id) throws UserNotFoundException {
        return walletRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public void deleteById(Long id) throws WalletNotFoundException {
        Wallet wallet = walletRepository.findByUserId(id).orElseThrow(() -> new WalletNotFoundException(id));
        walletRepository.deleteById(wallet.getId());
    }

    @Transactional
    public Wallet deposit(Long id, BigDecimal amount) throws WalletNotFoundException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be > 0");
        }

        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));

        wallet.setBalance(wallet.getBalance().add(amount));

        return wallet;
    }

    @Transactional
    public Wallet withdraw(Long id, BigDecimal amount) throws WalletNotFoundException, InsufficientFundsException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be > 0");
        }

        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(id);
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));

        return wallet;
    }

    @Transactional
    public Wallet transfer(Long sourceId, Long destinationId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be > 0");
        }

        Wallet sourceWallet = walletRepository.findById(sourceId)
                .orElseThrow(() -> new WalletNotFoundException(sourceId));

        Wallet destinationWallet = walletRepository.findById(destinationId)
                .orElseThrow(() -> new WalletNotFoundException(destinationId));

        if (sourceWallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(sourceId);
        }

        sourceWallet.setBalance(sourceWallet.getBalance().subtract(amount));
        destinationWallet.setBalance(destinationWallet.getBalance().add(amount));

        return sourceWallet;
    }
}
