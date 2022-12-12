package by.hackaton.bookcrossing.repository;

import by.hackaton.bookcrossing.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByOwner_Email(String email);

    Optional<Book> findByIdAndOwner_Email(Long id, String email);

    List<Book> findByReceiver_Email(String email);

    Optional<Book> findByIdAndReceiver_Email(Long id, String email);

    Optional<Book> findByISBN(String ISBN);

    @Query("SELECT b FROM Book b WHERE b.status = 'AVAILABLE'")
    List<Book> findByStatusAVAILABLE();
}
