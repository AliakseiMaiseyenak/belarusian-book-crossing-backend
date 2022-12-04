package by.hackaton.bookcrossing.controller;

import by.hackaton.bookcrossing.dto.EmailDto;
import by.hackaton.bookcrossing.dto.request.LoginRequest;
import by.hackaton.bookcrossing.dto.security.AuthResponse;
import by.hackaton.bookcrossing.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<Void> signIn(@RequestBody @Valid LoginRequest login) {
        authService.signIn(login);
        return ok().build();
    }

    @GetMapping("/verify/mail")
    public ResponseEntity<Void> verifyMail(@RequestParam("email") String email, @RequestParam("code") String code) {
        authService.signUpConfirm(email, code);
        return ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest login) {
        return ok(authService.login(login));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid EmailDto dto) {
        authService.resetPassword(dto.getEmail());
        return ok().build();
    }

    @GetMapping("/delete/{username}")
    public ResponseEntity<Void> deleteLast(@PathVariable String email){
        authService.deleteByEmail(email);
        return ok().build();
    }

    @GetMapping("/delete")
    public ResponseEntity<Void> deleteLast(){
        authService.deleteLast();
        return ok().build();
    }

    @GetMapping("/check/{username}")
    public ResponseEntity<Void> checkUsername(@PathVariable String username) {
        if (!authService.isUsernameExist(username)) {
            return ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
