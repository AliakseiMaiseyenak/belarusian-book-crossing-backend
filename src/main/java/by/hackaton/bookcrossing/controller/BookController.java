package by.hackaton.bookcrossing.controller;

import by.hackaton.bookcrossing.dto.BookDto;
import by.hackaton.bookcrossing.dto.BookFilter;
import by.hackaton.bookcrossing.dto.BookShortDto;
import by.hackaton.bookcrossing.entity.Account;
import by.hackaton.bookcrossing.entity.Book;
import by.hackaton.bookcrossing.repository.AccountRepository;
import by.hackaton.bookcrossing.repository.BookRepository;
import by.hackaton.bookcrossing.service.BookService;
import by.hackaton.bookcrossing.util.AuthUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;
    private AccountRepository accountRepository;

    public BookController(BookService bookService, AccountRepository accountRepository) {
        this.bookService = bookService;
        this.accountRepository = accountRepository;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks() {
        List<BookDto> books = bookService.getBooks();
        return ok(books);
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookDto>> getMyBooks(Authentication auth) {
        String email = AuthUtils.getEmailFromAuth(auth);
        List<BookDto> books = bookService.getMyBooks(email);
        return ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable("id") Long id) {
        return ok(bookService.getBookById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<String>> getBooksByFilter(BookFilter filter) {
        List<String> books = bookService.autoComplete(filter);
        return ok(books);
    }

    @GetMapping("/find")
    public ResponseEntity<BookShortDto> getBooksByISBN(@PathParam("isbn") String isbn) {
        BookShortDto book = bookService.findBookByISBN(isbn);
        return ok(book);
    }

    @PostMapping
    public ResponseEntity<Void> createBook(@RequestBody @Valid BookDto dto, Authentication auth) {
        Account account = null;
        if (auth != null) {
            String email = AuthUtils.getEmailFromAuth(auth);
            account = accountRepository.findByEmail(email).orElseThrow();
        }
        bookService.createBook(dto, account);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable("id") Long id, @RequestParam BookDto dto) {
        bookService.updateBook(id, dto);
        return ok().build();
    }

    /*@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        bookRepository.deleteById(id);
        return ok().build();
    }*/
}
