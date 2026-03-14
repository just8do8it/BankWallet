package com.wallet.bankwallet.service;

import com.wallet.bankwallet.entity.User;
import com.wallet.bankwallet.entity.Wallet;
import com.wallet.bankwallet.exception.UserNotFoundException;
import com.wallet.bankwallet.exception.WalletNotFoundException;
import com.wallet.bankwallet.repository.UserRepository;
import com.wallet.bankwallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User getByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Transactional
    public User save(User user) {
        User newUser = userRepository.save(user);

        Wallet wallet = new Wallet();
        wallet.setUser(newUser);
        wallet.setBalance(BigDecimal.ZERO);
        walletRepository.save(wallet);

        return newUser;
    }

    public void deleteById(Long id) throws UserNotFoundException {
        Wallet wallet = walletRepository.findByUserId(id).orElseThrow(() -> new UserNotFoundException(id));
        walletRepository.deleteById(wallet.getId());
        userRepository.deleteById(id);
    }
}
