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
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Book> books;
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private List<Book> sentBooks;
    private Double latitude;
    private Double longitude;
    private String contact;
    private boolean enabled = true;
    private Boolean deleted = false;

    @Enumerated(EnumType.STRING)
    private Role role;
    private byte[] avatar;
}
