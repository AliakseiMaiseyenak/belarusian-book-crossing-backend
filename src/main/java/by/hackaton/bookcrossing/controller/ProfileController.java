package by.hackaton.bookcrossing.controller;

import by.hackaton.bookcrossing.dto.AccountDto;
import by.hackaton.bookcrossing.dto.AccountShortDto;
import by.hackaton.bookcrossing.dto.LoginRequest;
import by.hackaton.bookcrossing.service.AccountService;
import by.hackaton.bookcrossing.util.AuthUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private AccountService accountService;

    public ProfileController(AccountService service) {
        this.accountService = service;
    }

    @GetMapping
    public ResponseEntity<AccountDto> getUser(Authentication auth) {
        return ok(accountService.getUser(auth.getName()));
    }

    @PutMapping
    public ResponseEntity<AccountDto> updateUser(@RequestBody AccountDto dto, Authentication auth) {
        return ok(accountService.updateUser(auth.getName(), dto));
    }

    @GetMapping("/{username}")
    public ResponseEntity<AccountShortDto> getUserByUsername(@PathVariable("username") String username) {
        return ok(accountService.getUserByUsername(username));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody LoginRequest login, Authentication auth) {
        accountService.resetPassword(login.getPassword(), AuthUtils.getEmailFromAuth(auth));
        return ok().build();
    }
}
