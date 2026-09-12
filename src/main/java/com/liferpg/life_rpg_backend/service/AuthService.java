package com.liferpg.life_rpg_backend.service;

import com.liferpg.life_rpg_backend.entity.Character;
import com.liferpg.life_rpg_backend.entity.User;
import com.liferpg.life_rpg_backend.repository.CharacterRepository;
import com.liferpg.life_rpg_backend.repository.UserRepository;
import com.liferpg.life_rpg_backend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final CharacterRepository characterRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       CharacterRepository characterRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.characterRepository = characterRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User register(String name, String email, String password) {

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        String encryptedPassword =
                passwordEncoder.encode(password);

        User user = new User(
                name,
                email,
                encryptedPassword
        );

        User savedUser = userRepository.save(user);

        Character character = new Character();

        character.setUser(savedUser);

        characterRepository.save(character);

        return savedUser;
    }

    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid email or password"
                        ));

        if (!passwordEncoder.matches(
                password,
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }

        return jwtService.generateToken(
                user.getEmail()
        );
    }
}