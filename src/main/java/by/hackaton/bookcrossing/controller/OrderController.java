package by.hackaton.bookcrossing.controller;

import by.hackaton.bookcrossing.dto.BookDto;
import by.hackaton.bookcrossing.dto.CreateOrderDTO;
import by.hackaton.bookcrossing.dto.OrderDto;
import by.hackaton.bookcrossing.dto.request.OrderRequest;
import by.hackaton.bookcrossing.repository.OrderRepository;
import by.hackaton.bookcrossing.service.OrderService;
import by.hackaton.bookcrossing.util.AuthUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderRepository orderRepository;
    private OrderService orderService;
    private ModelMapper modelMapper;

    public OrderController(OrderRepository orderRepository, OrderService orderService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getReceivedBooks(Authentication auth) {
        String email = AuthUtils.getEmailFromAuth(auth);
        List<OrderDto> orders = orderRepository.findByReceiver_Username(email).stream().map(o -> modelMapper.map(o, OrderDto.class)).collect(Collectors.toList());
        return ok(orders);
    }

    @PostMapping("/sent")
    public ResponseEntity<BookDto> sendBook(@RequestBody CreateOrderDTO dto, Authentication auth) {
        return ok(orderService.sendBook(dto, AuthUtils.getEmailFromAuth(auth)));
    }

    @PostMapping("/cancel")
    public ResponseEntity<BookDto> cancelSending(@RequestBody OrderRequest request, Authentication auth) {
        return ok(orderService.cancel(request.bookId, AuthUtils.getEmailFromAuth(auth)));
    }

    /*@PutMapping("/sent")
    public ResponseEntity<Void> sendBook(@RequestBody OrderRequest request, Authentication auth) {
        orderService.receiveBook(request.bookId, AuthUtils.getEmailFromAuth(auth));
        return ok().build();
    }*/

    @PutMapping("/receive")
    public ResponseEntity<Void> receiveBook(@RequestBody OrderRequest request, Authentication auth) {
        orderService.receiveBook(request.bookId, AuthUtils.getEmailFromAuth(auth));
        return ok().build();
    }
}
