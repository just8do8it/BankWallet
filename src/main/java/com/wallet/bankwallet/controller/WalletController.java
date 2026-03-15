package com.wallet.bankwallet.controller;

import com.wallet.bankwallet.entity.Wallet;
import com.wallet.bankwallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @GetMapping
    public List<Wallet> getAllWallets() {
        return walletService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> getWalletById(@PathVariable Long id) {
        return ResponseEntity.ok(walletService.findById(id));
    }

    @PostMapping("/{id}/deposit/{amount}")
    public ResponseEntity<Wallet> deposit(@PathVariable Long id, @PathVariable BigDecimal amount) {
        return ResponseEntity.ok(walletService.deposit(id, amount));
    }

    @PostMapping("/{id}/withdraw/{amount}")
    public ResponseEntity<Wallet> withdraw(@PathVariable Long id, @PathVariable BigDecimal amount) {
        return ResponseEntity.ok(walletService.withdraw(id, amount));
    }

    @PostMapping("/{sourceId}/{destinationId}/{amount}")
    public ResponseEntity<Wallet> transfer(@PathVariable Long sourceId,
                                                @PathVariable Long destinationId,
                                                @PathVariable BigDecimal amount) {
        return ResponseEntity.ok(walletService.transfer(sourceId, destinationId, amount));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWallet(@PathVariable Long id) {
        walletService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
