package by.hackaton.bookcrossing.service.impl;

import by.hackaton.bookcrossing.dto.AccountDto;
import by.hackaton.bookcrossing.dto.LoginRequest;
import by.hackaton.bookcrossing.dto.security.AuthResponse;
import by.hackaton.bookcrossing.dto.security.Token;
import by.hackaton.bookcrossing.dto.security.TokenProvider;
import by.hackaton.bookcrossing.entity.Account;
import by.hackaton.bookcrossing.entity.VerificationStatus;
import by.hackaton.bookcrossing.entity.enums.Role;
import by.hackaton.bookcrossing.repository.AccountRepository;
import by.hackaton.bookcrossing.repository.VerificationStatusRepository;
import by.hackaton.bookcrossing.service.AuthService;
import by.hackaton.bookcrossing.service.EmailService;
import by.hackaton.bookcrossing.service.exceptions.EmailConfirmationException;
import by.hackaton.bookcrossing.service.exceptions.LogicalException;
import by.hackaton.bookcrossing.service.exceptions.ServerError;
import by.hackaton.bookcrossing.service.exceptions.WrongPasswordException;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;
    private ModelMapper modelMapper;
    private AccountRepository accountRepository;
    private VerificationStatusRepository verificationStatusRepository;
    private EmailService emailService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, TokenProvider tokenProvider,
                           ModelMapper modelMapper, AccountRepository accountRepository,
                           VerificationStatusRepository verificationStatusRepository, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
        this.verificationStatusRepository = verificationStatusRepository;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public void signIn(LoginRequest request) {
        if (accountRepository.existsByEmail(request.getEmail().toLowerCase())) {
            throw new LogicalException(ServerError.EMAIL_ALREADY_EXISTS);
        }
        Account account = modelMapper.map(request, Account.class);
        account.setRole(Role.USER);
        account = accountRepository.save(account);
        String verificationCode = RandomStringUtils.randomAlphabetic(32);
        VerificationStatus status = new VerificationStatus(account.getEmail(), verificationCode);
        verificationStatusRepository.save(status);
        emailService.sendMessage(request.getEmail(),"VERIFY_MAIL_SUBJECT", "https://belarusian-bookcrossing.herokuapp.com/api/auth/verify/mail?email=" + account.getEmail() + "&code=" + verificationCode);
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
        Account account = accountRepository.findByEmail(request.getEmail()).orElseThrow();
        if (!account.getPassword().equals(request.getPassword())) {
            throw new WrongPasswordException(ServerError.WRONG_PASSWORD);
        }
        if (!account.isEnabled()) {
            throw new EmailConfirmationException(ServerError.EMAIL_NOT_CONFIRMED);
        }
        Authentication authentication = authenticate(request);
        String accessToken = tokenProvider.createToken(authentication);
        return new AuthResponse(new Token(accessToken), getAccountByEmail(request.getEmail()));
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
