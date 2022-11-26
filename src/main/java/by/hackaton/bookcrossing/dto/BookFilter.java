package by.hackaton.bookcrossing.dto;

import lombok.Data;

@Data
public class BookFilter {
    private String text;
    private FieldName field;
}
