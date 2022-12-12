package by.hackaton.bookcrossing.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.time.Instant;

@MappedSuperclass
@EnableJpaAuditing
@Getter
@Setter
public class BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    private Instant createdDate;
    @CreatedBy
    private String createdBy;

}
