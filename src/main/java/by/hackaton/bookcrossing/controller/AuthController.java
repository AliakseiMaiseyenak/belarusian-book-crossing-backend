package by.hackaton.bookcrossing.controller;

import by.hackaton.bookcrossing.dto.LoginRequest;
import by.hackaton.bookcrossing.dto.security.AuthResponse;
import by.hackaton.bookcrossing.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<Void> signIn(@RequestBody LoginRequest login) {
        authService.signIn(login);
        return ok().build();
    }

    @GetMapping("/verify/mail")
    public void verifyMail(@RequestParam("email") String email, @RequestParam("code") String code) {
        authService.signUpConfirm(email, code);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest login) {
        return ok(authService.login(login));
    }

    @GetMapping("/delete/{id}")
    public void deleteLast(@PathVariable long id){
        authService.deleteLast(id);
    }
}
