package by.hackaton.bookcrossing.dto.response;

import lombok.Data;

@Data
public class AccountShortResponse {
    public Long id;
    public String username;
    public Double longitude;
    public Double latitude;
    public String contact;
}
