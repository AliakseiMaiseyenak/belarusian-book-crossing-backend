package by.hackaton.bookcrossing.controller;

import by.hackaton.bookcrossing.dto.BookDto;
import by.hackaton.bookcrossing.dto.BookFilter;
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
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;
    private BookRepository bookRepository;
    private AccountRepository accountRepository;
    private ModelMapper modelMapper;

    public BookController(BookService bookService, BookRepository bookRepository,
                          AccountRepository accountRepository, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks() {
        List<BookDto> books = bookRepository.findAll().stream().map(b -> modelMapper.map(b, BookDto.class)).collect(toList());
        return ok(books);
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookDto>> getMyBooks(Authentication auth) {
        String email = AuthUtils.getEmailFromAuth(auth);
        List<BookDto> books = bookRepository.findByOwner_Username(email).stream().map(b -> modelMapper.map(b, BookDto.class)).collect(toList());
        return ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable("id") Long id) {
        Book book = bookRepository.findById(id).orElseThrow();
        return ok(modelMapper.map(book, BookDto.class));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<String>> getBooksByFilter(BookFilter filter) {
        List<String> books = bookService.autoComplete(filter);
        return ok(books);
    }

    @PostMapping
    public ResponseEntity<Void> createBook(@RequestBody @Valid BookDto dto, Authentication auth) {
        Book book = modelMapper.map(dto, Book.class);
        if (auth != null) {
            String email = AuthUtils.getEmailFromAuth(auth);
            Account account = accountRepository.findByEmail(email).orElseThrow();
            book.setOwner(account);
        }
        bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable("id") Long id, @RequestParam BookDto dto) {
        Book book = bookRepository.findById(id).orElseThrow();
        modelMapper.map(book, dto);
        bookRepository.save(book);
        return ok().build();
    }

    /*@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        bookRepository.deleteById(id);
        return ok().build();
    }*/
}
