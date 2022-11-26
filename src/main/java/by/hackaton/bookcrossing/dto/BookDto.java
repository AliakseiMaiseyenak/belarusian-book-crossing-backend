package by.hackaton.bookcrossing.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

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
    private boolean sendStatus;
    private String language;
}
