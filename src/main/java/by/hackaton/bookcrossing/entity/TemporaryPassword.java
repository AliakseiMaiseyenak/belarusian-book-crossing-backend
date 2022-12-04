package by.hackaton.bookcrossing.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TemporaryPassword {
    @Id
    private String email;
    private String code;

    public TemporaryPassword(String email) {
        this.email = email;
    }
}
