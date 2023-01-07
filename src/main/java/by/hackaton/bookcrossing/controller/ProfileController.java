package by.hackaton.bookcrossing.controller;

import by.hackaton.bookcrossing.dto.AccountDto;
import by.hackaton.bookcrossing.dto.AccountShortDto;
import by.hackaton.bookcrossing.dto.request.PasswordRequest;
import by.hackaton.bookcrossing.dto.response.AccountProfileResponse;
import by.hackaton.bookcrossing.service.AccountService;
import by.hackaton.bookcrossing.util.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private AccountService accountService;

    public ProfileController(AccountService service) {
        this.accountService = service;
    }

    @GetMapping
    public ResponseEntity<AccountProfileResponse> getUser(Authentication auth) {
        return ok(accountService.getUser(AuthUtils.getEmailFromAuth(auth)));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> updateUser(@RequestBody AccountDto dto, Authentication auth) {
        accountService.updateUser(AuthUtils.getEmailFromAuth(auth), dto);
        return ok().build();
    }

    @PutMapping("/avatar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<AccountDto> updateUserAvatar(@RequestParam MultipartFile avatar, Authentication auth) {
        accountService.updateUserAvatar(AuthUtils.getEmailFromAuth(auth), avatar);
        return ok().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<AccountShortDto> getUserByUsername(@PathVariable("username") String username) {
        return ok(accountService.getUserByUsername(username));
    }

    @PutMapping("/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> changePassword(@RequestBody PasswordRequest request, Authentication auth) {
        accountService.changePassword(request.getPassword(), AuthUtils.getEmailFromAuth(auth));
        return ok().build();
    }

    @GetMapping("/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> changePasswordWithCode(@RequestParam PasswordRequest request) {
        accountService.changePasswordWithCode(request);
        return ok().build();
    }
}
