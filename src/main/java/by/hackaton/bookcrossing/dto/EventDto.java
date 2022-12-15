package by.hackaton.bookcrossing.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventDto {
    private LocalDate date;
    private String event;
}
