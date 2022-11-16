package by.hackaton.bookcrossing.dto;

import lombok.Data;

@Data
public class BookFilter {
    public String text;
    public FieldName field;
}
