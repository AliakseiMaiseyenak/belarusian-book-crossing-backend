package by.hackaton.bookcrossing.service.impl;

import by.hackaton.bookcrossing.dto.AccountDto;
import by.hackaton.bookcrossing.dto.AccountShortDto;
import by.hackaton.bookcrossing.dto.request.PasswordRequest;
import by.hackaton.bookcrossing.dto.response.AccountProfileResponse;
import by.hackaton.bookcrossing.entity.Account;
import by.hackaton.bookcrossing.repository.AccountRepository;
import by.hackaton.bookcrossing.repository.TemporaryPasswordRepository;
import by.hackaton.bookcrossing.service.AccountService;
import by.hackaton.bookcrossing.service.exceptions.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private TemporaryPasswordRepository passwordRepository;
    private ModelMapper modelMapper;

    public AccountServiceImpl(AccountRepository accountRepository,
                              TemporaryPasswordRepository passwordRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.passwordRepository = passwordRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AccountProfileResponse getUser(String email) {
        Account user = accountRepository.findByEmail(email).orElseThrow(
                () -> new BadRequestException("Account not fount")
        );
        return modelMapper.map(user, AccountProfileResponse.class);
    }

    @Override
    public AccountShortDto getUserByUsername(String username) {
        Account user = accountRepository.findByUsername(username).orElseThrow(
                () -> new BadRequestException("Account not fount")
        );
        return modelMapper.map(user, AccountShortDto.class);
    }

    @Override
    public void updateUser(String email, AccountDto dto) {
        Account user = accountRepository.findByEmail(email).orElseThrow(
                () -> new BadRequestException("Account not fount")
        );
        modelMapper.map(user, dto);
        accountRepository.save(user);
    }

    @Override
    public void updateUserAvatar(String email, MultipartFile avatar) {
        try {
            Account user = accountRepository.findByEmail(email).orElseThrow(
                    () -> new BadRequestException("Account not fount")
            );
            user.setAvatar(avatar.getBytes());
            accountRepository.save(user);
        } catch (IOException ex) {

        }
    }

    @Override
    public void changePassword(String newPassword, String email) {
        Account account = accountRepository.findByEmail(email).orElseThrow(
                () -> new BadRequestException("Account not fount")
        );
        account.setPassword(newPassword);
        accountRepository.save(account);
    }

    @Override
    public void changePasswordWithCode(PasswordRequest request) {
        if (passwordRepository.existsByEmailAndCode(request.getEmail(), request.getCode())) {
            Account account = accountRepository.findByEmail(request.getEmail()).orElseThrow(
                    () -> new BadRequestException("Account not fount")
            );
            account.setPassword(request.getPassword());
            accountRepository.save(account);
        } else {
            throw new BadRequestException("Email and code not found");
        }
    }
}
