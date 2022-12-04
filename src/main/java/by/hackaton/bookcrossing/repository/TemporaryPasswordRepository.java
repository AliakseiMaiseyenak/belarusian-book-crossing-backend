package by.hackaton.bookcrossing.repository;

import by.hackaton.bookcrossing.entity.TemporaryPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporaryPasswordRepository extends JpaRepository<TemporaryPassword, String> {

    boolean existsByEmailAndCode(String email, String code);
}
