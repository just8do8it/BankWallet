package com.wallet.bankwallet.controller;

import com.wallet.bankwallet.model.Wallet;
import com.wallet.bankwallet.model.TransferRequest;
import com.wallet.bankwallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Operation(
            summary = "List all wallets",
            description = "Returns a list of all wallets with their current balances."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallets listed successfully")
    })
    public List<Wallet> getAllWallets() {
        return walletService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get wallet by id",
            description = "Returns the wallet with the given id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet found"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "Wallet not found")
    })
    public ResponseEntity<Wallet> getWalletById(@PathVariable @NotNull @Positive Long id) {
        return ResponseEntity.ok(walletService.findById(id));
    }

    @PostMapping("/{id}/deposit/{amount}")
    @Operation(
            summary = "Deposit into wallet",
            description = "Increases the balance of the wallet by the given amount."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deposit successful"),
            @ApiResponse(responseCode = "400", description = "Invalid id or amount supplied"),
            @ApiResponse(responseCode = "404", description = "Wallet not found")
    })
    public ResponseEntity<Wallet> deposit(@PathVariable @NotNull @Positive Long id,
                                          @PathVariable BigDecimal amount) {
        return ResponseEntity.ok(walletService.deposit(id, amount));
    }

    @PostMapping("/{id}/withdraw/{amount}")
    @Operation(
            summary = "Withdraw from wallet",
            description = "Decreases the balance of the wallet by the given amount."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Withdrawal successful"),
            @ApiResponse(responseCode = "400", description = "Invalid id or amount, or insufficient funds"),
            @ApiResponse(responseCode = "404", description = "Wallet not found")
    })
    public ResponseEntity<Wallet> withdraw(@PathVariable @NotNull @Positive Long id,
                                           @PathVariable BigDecimal amount) {
        return ResponseEntity.ok(walletService.withdraw(id, amount));
    }

    @PostMapping("/transfer")
    @Operation(
            summary = "Transfer between wallets",
            description = "Transfers the given amount from the source wallet to the destination wallet."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transfer successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request data or insufficient funds"),
            @ApiResponse(responseCode = "404", description = "Source or destination wallet not found")
    })
    public ResponseEntity<Wallet> transfer(@Valid @RequestBody TransferRequest request) {
        return ResponseEntity.ok(
                walletService.transfer(request.getSourceId(), request.getDestinationId(), request.getAmount())
        );
    }
}
