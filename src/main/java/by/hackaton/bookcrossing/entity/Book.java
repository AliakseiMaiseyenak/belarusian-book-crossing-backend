package by.hackaton.bookcrossing.entity;

import by.hackaton.bookcrossing.entity.enums.BookStatus;
import by.hackaton.bookcrossing.entity.enums.Obtain;
import by.hackaton.bookcrossing.entity.enums.SendType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Book extends BaseEntity {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    private int year;
    private String city;
    private String country;
    private String ISBN;
    private String contacts;
    private String additional;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Account owner;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Account receiver;
    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.AVAILABLE;
    @Enumerated(EnumType.STRING)
    private SendType sendType;
    @Enumerated(EnumType.STRING)
    private Obtain obtain;
    private String language;
    @OneToMany(mappedBy = "book")
    private List<BookOrder> bookOrders;

}
