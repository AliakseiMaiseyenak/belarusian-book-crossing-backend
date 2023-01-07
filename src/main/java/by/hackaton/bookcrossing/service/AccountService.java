package by.hackaton.bookcrossing.service;

import by.hackaton.bookcrossing.dto.AccountDto;
import by.hackaton.bookcrossing.dto.AccountShortDto;
import by.hackaton.bookcrossing.dto.request.PasswordRequest;
import by.hackaton.bookcrossing.dto.response.AccountProfileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {
    AccountProfileResponse getUser(String email);
    AccountShortDto getUserByUsername(String username);
    void updateUser(String email, AccountDto dto);
    void changePassword(String newPassword, String email);

    void changePasswordWithCode(PasswordRequest request);

    void updateUserAvatar(String emailFromAuth, MultipartFile avatar);
}
