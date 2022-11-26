package by.hackaton.bookcrossing.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BookShortDto {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    private int year;
    private String ISBN;
    private String language;
}
