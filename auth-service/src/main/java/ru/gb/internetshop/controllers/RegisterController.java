package ru.gb.internetshop.controllers;

import api.RegisterUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.internetshop.entities.User;
import ru.gb.internetshop.exceptions.AppError;
import ru.gb.internetshop.exceptions.UsernameIsAlreadyPresent;
import ru.gb.internetshop.services.UserService;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity registrateNewUser(@RequestBody RegisterUserDto registerUserDto) {
        // TODO полностью реализовать метод, как считаете нужным
        //  ниже всего лишь пример хеширования паролей
        return userService.registerNewUser(registerUserDto);
    }
}
