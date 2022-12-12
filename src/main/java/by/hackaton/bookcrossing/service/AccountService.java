package by.hackaton.bookcrossing.service;

import by.hackaton.bookcrossing.dto.AccountDto;
import by.hackaton.bookcrossing.dto.AccountShortDto;
import by.hackaton.bookcrossing.dto.request.PasswordRequest;

public interface AccountService {
    AccountDto getUser(String email);
    AccountShortDto getUserByUsername(String username);
    AccountDto updateUser(String email, AccountDto dto);
    void changePassword(String newPassword, String email);

    void changePasswordWithCode(PasswordRequest request);
}
