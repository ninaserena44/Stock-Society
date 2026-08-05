package com.stocksociety.stocksociety.service;

import com.stocksociety.stocksociety.model.RegistrationForm;
import com.stocksociety.stocksociety.model.Role;
import com.stocksociety.stocksociety.model.User;
import com.stocksociety.stocksociety.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(RegistrationForm form) {
        String normalizedEmail = form.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new IllegalArgumentException(
                "An account already exists with this email address."
            );
        }

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            throw new IllegalArgumentException(
                "The passwords do not match."
            );
        }

        User user = new User();

        user.setFirstName(form.getFirstName().trim());
        user.setLastName(form.getLastName().trim());
        user.setEmail(normalizedEmail);

        // Never save the original plain-text password.
        user.setPassword(passwordEncoder.encode(form.getPassword()));

        // All public registrations begin as customers.
        user.setRole(Role.CUSTOMER);
        user.setEnabled(true);

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        return userRepository.findByEmailIgnoreCase(email.trim())
                .orElseThrow(() ->
                    new UsernameNotFoundException(
                        "No user was found with that email address."
                    )
                );
    }
}
