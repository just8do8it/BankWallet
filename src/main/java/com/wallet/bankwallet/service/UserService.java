package com.wallet.bankwallet.service;

import com.wallet.bankwallet.entity.User;
import com.wallet.bankwallet.entity.Wallet;
import com.wallet.bankwallet.exception.UserNotFoundException;
import com.wallet.bankwallet.repository.UserRepository;
import com.wallet.bankwallet.repository.WalletRepository;
import com.wallet.bankwallet.request.UserRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
    public User save(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        userRepository.save(user);

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(BigDecimal.ZERO);
        walletRepository.save(wallet);

        return user;
    }

    @Transactional
    public User update(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }

    public void deleteById(Long id) throws UserNotFoundException {
        Wallet wallet = walletRepository.findByUserId(id).orElseThrow(() -> new UserNotFoundException(id));
        walletRepository.deleteById(wallet.getId());
        userRepository.deleteById(id);
    }
}
