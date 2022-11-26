package by.hackaton.bookcrossing.entity;

import by.hackaton.bookcrossing.entity.enums.OrganizationType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Organization {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private OrganizationType type;
    @NotNull
    private String city;
    @NotNull
    private String country;
    @NotNull
    private String address;
    private String workTime;
    private String description;
    private String site;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
}
