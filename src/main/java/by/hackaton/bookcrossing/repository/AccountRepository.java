package by.hackaton.bookcrossing.repository;

import by.hackaton.bookcrossing.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT u FROM Account u WHERE u.email = :username")
    Account getUserByUsername(@Param("username") String username);

    boolean existsByEmail(String email);

    boolean existsByUsernameIgnoreCase(String username);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);

    @Modifying
    @Query("UPDATE Account a SET a.enabled = TRUE where a.email = :email")
    void verifyAccount(@Param("email") String email);
}
