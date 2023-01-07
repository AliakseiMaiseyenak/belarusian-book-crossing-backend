package by.hackaton.bookcrossing.entity;

import by.hackaton.bookcrossing.entity.enums.BookStatus;
import by.hackaton.bookcrossing.entity.enums.Obtain;
import by.hackaton.bookcrossing.entity.enums.SendType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    @OneToMany(mappedBy = "book")
    private List<Author> authors;
    private int year;
    private String city;
    private String country;
    private String ISBN;
    private String contacts;
    private String additional;
    private Double latitude;
    private Double longitude;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Account owner;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Account receiver;
    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.AVAILABLE;
    @ElementCollection
    @CollectionTable(
            name = "book_send_type",
            joinColumns = @JoinColumn(name = "book_id")
    )
    private List<SendType> sendTypes;
    @Enumerated(EnumType.STRING)
    private Obtain obtain;
    private String language;
    @OneToMany(mappedBy = "book")
    private List<BookOrder> bookOrders;
    private byte[] image;

}
