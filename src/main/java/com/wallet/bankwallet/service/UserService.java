package com.wallet.bankwallet.service;

import com.wallet.bankwallet.model.Currency;
import com.wallet.bankwallet.model.User;
import com.wallet.bankwallet.model.Wallet;
import com.wallet.bankwallet.exception.UserNotFoundException;
import com.wallet.bankwallet.repository.UserRepository;
import com.wallet.bankwallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
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

    public User findByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Transactional
    public User save(String name, String email) {
        log.info("User with name={} and email={} created", name, email);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        userRepository.save(user);

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCurrency(Currency.EUR);
        walletRepository.save(wallet);

        return user;
    }

    @Transactional
    public User update(Long id, String name, String email) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setName(name);
        user.setEmail(email);

        return userRepository.save(user);
    }

    public void deleteById(Long id) throws UserNotFoundException {
        Wallet wallet = walletRepository.findByUserId(id).orElseThrow(() -> new UserNotFoundException(id));
        walletRepository.deleteById(wallet.getId());
        userRepository.deleteById(id);
    }
}
