package by.hackaton.bookcrossing.service;

import by.hackaton.bookcrossing.dto.BookDto;
import by.hackaton.bookcrossing.dto.request.CreateOrderRequest;
import by.hackaton.bookcrossing.entity.Account;
import by.hackaton.bookcrossing.entity.Book;
import by.hackaton.bookcrossing.entity.BookOrder;
import by.hackaton.bookcrossing.entity.enums.BookStatus;
import by.hackaton.bookcrossing.entity.enums.SendType;
import by.hackaton.bookcrossing.repository.AccountRepository;
import by.hackaton.bookcrossing.repository.BookRepository;
import by.hackaton.bookcrossing.repository.OrderRepository;
import by.hackaton.bookcrossing.service.exceptions.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private BookRepository bookRepository;
    private AccountRepository accountRepository;
    private ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, BookRepository bookRepository, AccountRepository accountRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public BookDto sendBook(CreateOrderRequest dto, String email){
        Book book = bookRepository.findByIdAndOwner_Email(dto.getBookId(), email).orElseThrow(
                () -> new BadRequestException("Book not fount")
        );
        Account sendTo = accountRepository.findByUsername(dto.getReceiver()).orElseThrow(
                () -> new BadRequestException("Account not fount")
        );
        BookOrder bookOrder = BookOrder.builder().book(book).receiver(sendTo).sendType(dto.getSendType())
                .obtain(dto.getObtain()).build();
        //orderRepository.save(bookOrder);
        book.getBookOrders().add(bookOrder);
        book.setStatus(BookStatus.SENT);
        book.setReceiver(sendTo);
        bookRepository.save(book);
        return modelMapper.map(book, BookDto.class);
    }

    @Transactional
    public BookDto cancel(Long bookId, String email){
        Book book = bookRepository.findByIdAndOwner_Email(bookId, email).orElseThrow(
                () -> new BadRequestException("Book not fount")
        );
        book.setStatus(BookStatus.AVAILABLE);
        orderRepository.deleteActiveOrder(bookId);
        bookRepository.save(book);
        return modelMapper.map(book, BookDto.class);
    }

    @Transactional
    public BookDto receiveBook(Long bookId, String email){
        Book book = bookRepository.findByIdAndReceiver_Email(bookId, email).orElseThrow(
                () -> new BadRequestException("Book not fount")
        );
        BookOrder order = book.getBookOrders().stream().filter(BookOrder::isActive).findFirst().orElseThrow(
                () -> new BadRequestException("Order not fount")
        );
        if(order.getSendType().equals(SendType.FOREVER)){
            Account newOwner = accountRepository.findByEmail(email).orElseThrow(
                    () -> new BadRequestException("Account with email '" + email + "' not fount")
            );
            order.setActive(false);
            book.setOwner(newOwner);
            book.setReceiver(null);
            book.setStatus(BookStatus.NOT_AVAILABLE);
            bookRepository.save(book);
        }
        if(order.getSendType().equals(SendType.SOLD)){
            bookRepository.delete(book);
            return null;
        }
        if(order.getSendType().equals(SendType.TEMP)){
            book.setStatus(BookStatus.RECEIVED);
        }
        return modelMapper.map(book, BookDto.class);

    }
}
