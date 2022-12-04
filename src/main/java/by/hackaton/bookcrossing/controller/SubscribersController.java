package by.hackaton.bookcrossing.controller;

import by.hackaton.bookcrossing.dto.request.SubscriptionRequest;
import by.hackaton.bookcrossing.entity.Subscriber;
import by.hackaton.bookcrossing.repository.SubscriberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/subscribers")
public class SubscribersController {

    private SubscriberRepository subscriberRepository;

    public SubscribersController(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @GetMapping
    public ResponseEntity<List<Subscriber>> getAllSubscribers() {
        return ResponseEntity.ok(subscriberRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> createSubscriber(@Valid @RequestBody SubscriptionRequest request) {
        subscriberRepository.save(new Subscriber(request.getEmail()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
