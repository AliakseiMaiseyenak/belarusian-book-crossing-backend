package by.hackaton.bookcrossing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OnMapResponse {
    public AccountShortResponse account;
    public List<BookOnMapResponse> books;
}
