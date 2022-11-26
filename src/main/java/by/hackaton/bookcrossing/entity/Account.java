package by.hackaton.bookcrossing.entity;

import by.hackaton.bookcrossing.entity.enums.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    @Column(unique = true)
    private String username;
    private String password;
    @OneToMany(mappedBy = "owner")
    private List<Book> books;
    @OneToMany(mappedBy = "receiver")
    private List<BookOrder> bookOrders;
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Role role;
}
