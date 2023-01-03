package by.hackaton.bookcrossing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CatalogResponse {
    public AccountShortResponse account;
    public List<CatalogBookResponse> books;
}
