package by.hackaton.bookcrossing.controller;

import by.hackaton.bookcrossing.dto.BookDto;
import by.hackaton.bookcrossing.dto.BookFilter;
import by.hackaton.bookcrossing.dto.BookShortDto;
import by.hackaton.bookcrossing.dto.response.CreatedEntityIdResponse;
import by.hackaton.bookcrossing.dto.response.BookOnMapResponse;
import by.hackaton.bookcrossing.dto.response.OnMapResponse;
import by.hackaton.bookcrossing.entity.Account;
import by.hackaton.bookcrossing.repository.AccountRepository;
import by.hackaton.bookcrossing.service.BookService;
import by.hackaton.bookcrossing.util.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        return ok(bookService.getBooks());
    }
    @GetMapping("/format")
    public ResponseEntity<List<OnMapResponse>> getBooksWithUser() {
        return ok(bookService.getBooksWithUser());
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookOnMapResponse>> getMyBooks(Authentication auth) {
        String email = AuthUtils.getEmailFromAuth(auth);
        return ok(bookService.getMyBooks(email));
    }

    @GetMapping("/sent")
    public ResponseEntity<List<BookOnMapResponse>> getSentBooks(Authentication auth) {
        String email = AuthUtils.getEmailFromAuth(auth);
        return ok(bookService.getSentBooks(email));
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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreatedEntityIdResponse> createBook(@RequestBody @Valid BookDto dto, Authentication auth) {
        Account account = null;
        if (auth != null) {
            String email = AuthUtils.getEmailFromAuth(auth);
            account = accountRepository.findByEmail(email).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account not fount")
            );
        }
        return ok(bookService.createBook(dto, account));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("id") Long id, @RequestParam BookDto dto, Authentication auth) {
        return ok(bookService.updateBook(id, dto, AuthUtils.getEmailFromAuth(auth)));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BookDto> updateBookStatus(@PathVariable("id") Long id, Authentication auth) {
        return ok(bookService.updateStatus(id, AuthUtils.getEmailFromAuth(auth)));
    }

    /*@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        bookRepository.deleteById(id);
        return ok().build();
    }*/
}
