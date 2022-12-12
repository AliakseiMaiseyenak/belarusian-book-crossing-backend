package by.hackaton.bookcrossing.service;

import by.hackaton.bookcrossing.entity.Book;
import by.hackaton.bookcrossing.repository.BookRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchedulerService {

    private BookRepository bookRepository;

    public SchedulerService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteOldBooks() {
        List<Book> oldBooks = bookRepository.findAll().stream()
                .filter(b -> (b.getOwner() == null && b.getCreatedDate().plus(7, ChronoUnit.DAYS).isBefore(Instant.now()))).collect(Collectors.toList());
        bookRepository.deleteAll(oldBooks);
    }
}
