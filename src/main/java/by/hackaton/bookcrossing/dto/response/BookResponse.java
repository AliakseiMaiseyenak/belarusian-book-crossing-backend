package by.hackaton.bookcrossing.dto.response;

import by.hackaton.bookcrossing.dto.AccountShortDto;
import by.hackaton.bookcrossing.dto.BookShortDto;
import by.hackaton.bookcrossing.entity.enums.BookStatus;
import by.hackaton.bookcrossing.entity.enums.SendMethod;
import by.hackaton.bookcrossing.entity.enums.SendType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BookResponse extends BookShortDto {
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
    private AccountShortDto receiver;
    private BookStatus status;
    private SendType sendType;
    private SendMethod sendMethod;
    private String language;
}
