package by.hackaton.bookcrossing.dto;

import by.hackaton.bookcrossing.entity.enums.Obtain;
import by.hackaton.bookcrossing.entity.enums.SendType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BookDto extends BookShortDto {
    private Long id;
    private int year;
    private String city;
    private String country;
    private String contacts;
    private String additional;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    private AccountShortDto owner;
    private List<SendType> sendTypes;
    private Obtain obtain;
    private String language;
}
