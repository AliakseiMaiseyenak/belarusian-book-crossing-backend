package by.hackaton.bookcrossing.dto;

import by.hackaton.bookcrossing.entity.enums.OrganizationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationDto {
    private Long id;
    private String name;
    private OrganizationType type;
    private String city;
    private String country;
    private String address;
    private String workTime;
    private String description;
    private String site;
    private Double latitude;
    private Double longitude;
}
