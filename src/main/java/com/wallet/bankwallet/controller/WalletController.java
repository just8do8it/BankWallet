package com.wallet.bankwallet.controller;

import com.wallet.bankwallet.model.Wallet;
import com.wallet.bankwallet.model.TransferRequest;
import com.wallet.bankwallet.service.WalletService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Wallet> getWalletById(@PathVariable @NotNull @Positive Long id) {
        return ResponseEntity.ok(walletService.findById(id));
    }

    @PostMapping("/{id}/deposit/{amount}")
    public ResponseEntity<Wallet> deposit(@PathVariable @NotNull @Positive Long id,
                                          @PathVariable BigDecimal amount) {
        return ResponseEntity.ok(walletService.deposit(id, amount));
    }

    @PostMapping("/{id}/withdraw/{amount}")
    public ResponseEntity<Wallet> withdraw(@PathVariable @NotNull @Positive Long id,
                                           @PathVariable BigDecimal amount) {
        return ResponseEntity.ok(walletService.withdraw(id, amount));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Wallet> transfer(@Valid @PathVariable TransferRequest request) {
        return ResponseEntity.ok(
                walletService.transfer(request.getSourceId(), request.getDestinationId(), request.getAmount())
        );
    }
}
