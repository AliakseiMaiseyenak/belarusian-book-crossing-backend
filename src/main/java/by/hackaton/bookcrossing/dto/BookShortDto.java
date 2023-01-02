package by.hackaton.bookcrossing.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class BookShortDto {
    @NotBlank
    private String title;
    private List<AuthorDto> authors;
    private int year;
    private String ISBN;
    private String language;
}
