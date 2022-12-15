package by.hackaton.bookcrossing.entity;

import by.hackaton.bookcrossing.entity.enums.Obtain;
import by.hackaton.bookcrossing.entity.enums.SendType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookOrder {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Account receiver;
    @Enumerated(EnumType.STRING)
    private SendType sendType;
    @Enumerated(EnumType.STRING)
    private Obtain obtain;
    @ColumnDefault("true")
    private boolean active;
}
