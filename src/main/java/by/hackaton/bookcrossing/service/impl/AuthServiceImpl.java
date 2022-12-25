package by.hackaton.bookcrossing.service.impl;

import by.hackaton.bookcrossing.dto.AccountDto;
import by.hackaton.bookcrossing.dto.request.LoginRequest;
import by.hackaton.bookcrossing.dto.request.SignInRequest;
import by.hackaton.bookcrossing.dto.security.AuthResponse;
import by.hackaton.bookcrossing.dto.security.Token;
import by.hackaton.bookcrossing.dto.security.TokenProvider;
import by.hackaton.bookcrossing.entity.Account;
import by.hackaton.bookcrossing.entity.TemporaryPassword;
import by.hackaton.bookcrossing.entity.VerificationStatus;
import by.hackaton.bookcrossing.entity.enums.Role;
import by.hackaton.bookcrossing.repository.AccountRepository;
import by.hackaton.bookcrossing.repository.TemporaryPasswordRepository;
import by.hackaton.bookcrossing.repository.VerificationStatusRepository;
import by.hackaton.bookcrossing.service.AuthService;
import by.hackaton.bookcrossing.service.EmailService;
import by.hackaton.bookcrossing.service.exceptions.EmailConfirmationException;
import by.hackaton.bookcrossing.service.exceptions.LogicalException;
import by.hackaton.bookcrossing.service.exceptions.ServerError;
import by.hackaton.bookcrossing.service.exceptions.WrongPasswordException;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;
    private ModelMapper modelMapper;
    private AccountRepository accountRepository;
    private VerificationStatusRepository verificationStatusRepository;
    private EmailService emailService;
    private TemporaryPasswordRepository temporaryPasswordRepository;

    public AuthServiceImpl(AuthenticationManager authenticationManager, TokenProvider tokenProvider,
                           ModelMapper modelMapper, AccountRepository accountRepository,
                           VerificationStatusRepository verificationStatusRepository, EmailService emailService,
                           TemporaryPasswordRepository temporaryPasswordRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
        this.verificationStatusRepository = verificationStatusRepository;
        this.emailService = emailService;
        this.temporaryPasswordRepository = temporaryPasswordRepository;
    }

    @Override
    @Transactional
    public AuthResponse signIn(SignInRequest request) {
        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new LogicalException(ServerError.EMAIL_ALREADY_EXISTS);
        }
        Account account = modelMapper.map(request, Account.class);
        account.setRole(Role.USER);
        account = accountRepository.save(account);
        String verificationCode = RandomStringUtils.randomAlphabetic(32);
        VerificationStatus status = new VerificationStatus(account.getEmail(), verificationCode);
        verificationStatusRepository.save(status);
        //emailService.sendMessage(request.getEmail(), "Рэгістрацыя", "https://belarusian-bookcrossing.herokuapp.com/api/auth/verify/mail?email=" + account.getEmail() + "&code=" + verificationCode);
        Authentication authentication = authenticate(new LoginRequest(request.getEmail(), request.getPassword()));
        String accessToken = tokenProvider.createToken(authentication);
        return new AuthResponse(new Token(accessToken), getAccountByEmail(request.getEmail()));
    }

    @Override
    public void resetPassword(String email) {
        if (!accountRepository.existsByEmail(email)) {
            throw new LogicalException("Email not found");
        }
        String verificationCode = RandomStringUtils.randomAlphabetic(32);
        TemporaryPassword temp = temporaryPasswordRepository.findById(email).orElse(new TemporaryPassword(email));
        temp.setCode(verificationCode);
        temporaryPasswordRepository.save(temp);
        //emailService.sendMessage(email, "Аднаўленне паролю", "https://belarusian-bookcrossing.herokuapp.com/api/auth/verify/mail?email=" + email + "&code=" + verificationCode);
    }

    @Override
    @Transactional
    public void signUpConfirm(String email, String code) {
        VerificationStatus status = verificationStatusRepository.findByEmailAndCode(email, code).orElseThrow();
        verificationStatusRepository.delete(status);
        accountRepository.verifyAccount(email);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Account account = accountRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account not fount")
        );
        if (!account.getPassword().equals(request.getPassword())) {
            throw new WrongPasswordException(HttpStatus.BAD_REQUEST, "Wrong password");
        }
        if (!account.isEnabled()) {
            throw new EmailConfirmationException(HttpStatus.METHOD_NOT_ALLOWED, "Email not confirmed");
        }
        Authentication authentication = authenticate(request);
        String accessToken = tokenProvider.createToken(authentication);
        return new AuthResponse(new Token(accessToken), getAccountByEmail(request.getEmail()));
    }

    @Override
    public void deleteByEmail(String email) {
        Optional<Account> account = accountRepository.findAll().stream().filter(a -> a.getEmail().equals(email)).findFirst();
        account.ifPresent(account1 -> accountRepository.delete(account1));
    }

    @Override
    public void deleteLast() {
        List<Account> accounts = accountRepository.findAll();
        accountRepository.delete(accounts.get(accounts.size() - 1));
    }

    @Override
    public boolean isUsernameExist(String username) {
        return accountRepository.existsByUsernameIgnoreCase(username);
    }

    private Authentication authenticate(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail().toLowerCase(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private AccountDto getAccountByEmail(String email) {
        Account account = accountRepository.findByEmail(email).orElseThrow();
        return modelMapper.map(account, AccountDto.class);
    }
}
