package by.hackaton.bookcrossing.service;

import by.hackaton.bookcrossing.dto.LoginRequest;
import by.hackaton.bookcrossing.dto.security.AuthResponse;

public interface AuthService {
    void signIn(LoginRequest login);
    void signUpConfirm(String email, String code);
    AuthResponse login(LoginRequest login);

    void deleteLast(long id);
}
