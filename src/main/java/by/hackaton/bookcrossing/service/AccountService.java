package by.hackaton.bookcrossing.service;

import by.hackaton.bookcrossing.dto.AccountDto;
import by.hackaton.bookcrossing.dto.AccountShortDto;
import by.hackaton.bookcrossing.dto.PasswordRequest;

public interface AccountService {
    AccountDto getUser(String username);
    AccountShortDto getUserByUsername(String username);
    AccountDto updateUser(String username, AccountDto dto);
    void changePassword(String newPassword, String email);

    void changePasswordWithCode(PasswordRequest request);
}
