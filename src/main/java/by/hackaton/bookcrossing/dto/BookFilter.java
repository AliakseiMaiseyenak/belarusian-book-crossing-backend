package by.hackaton.bookcrossing.dto;

import by.hackaton.bookcrossing.dto.enums.FieldName;
import lombok.Data;

@Data
public class BookFilter {
    private String text;
    private FieldName field;
}
