package by.hackaton.bookcrossing.service;

import by.hackaton.bookcrossing.dto.request.LoginRequest;
import by.hackaton.bookcrossing.dto.security.AuthResponse;

public interface AuthService {
    void signIn(LoginRequest login);
    void resetPassword(String email);
    void signUpConfirm(String email, String code);
    AuthResponse login(LoginRequest login);

    void deleteByEmail(String email);
    void deleteLast();

    boolean isUsernameExist(String username);
}
