package by.hackaton.bookcrossing.dto.response;

import by.hackaton.bookcrossing.dto.AuthorDto;
import by.hackaton.bookcrossing.entity.enums.Obtain;
import by.hackaton.bookcrossing.entity.enums.SendType;
import lombok.Data;

import java.util.List;

@Data
public class BookOnMapResponse {
    private Long id;
    private String title;
    private List<AuthorDto> authors;
    private int year;
    private String city;
    private String country;
    private List<SendType> sendTypes;
    private Obtain obtain;
}
