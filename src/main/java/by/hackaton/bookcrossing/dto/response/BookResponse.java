package by.hackaton.bookcrossing.dto.response;

import by.hackaton.bookcrossing.dto.AccountShortDto;
import by.hackaton.bookcrossing.dto.BookShortDto;
import by.hackaton.bookcrossing.entity.enums.BookStatus;
import by.hackaton.bookcrossing.entity.enums.Obtain;
import by.hackaton.bookcrossing.entity.enums.SendType;
import lombok.Data;

@Data
public class BookResponse extends BookShortDto {
    private Long id;
    private int year;
    private String city;
    private String country;
    private String contacts;
    private String additional;
    private Double latitude;
    private Double longitude;
    private AccountShortDto owner;
    private AccountShortDto receiver;
    private BookStatus status;
    private SendType sendType;
    private Obtain obtain;
    private String language;
}
