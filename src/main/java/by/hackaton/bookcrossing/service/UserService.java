package by.hackaton.bookcrossing.service;

import by.hackaton.bookcrossing.entity.Account;
import by.hackaton.bookcrossing.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private AccountRepository repository;

    public void processOAuthPostLogin(String username) {
        Account existUser = repository.getUserByUsername(username);

        if (existUser == null) {
            Account newUser = new Account();
            newUser.setUsername("Belarus_" + System.currentTimeMillis());
            newUser.setEmail(username);
            newUser.setEnabled(true);

            repository.save(newUser);

            log.info("Created new user: " + username);
        }

    }

}
