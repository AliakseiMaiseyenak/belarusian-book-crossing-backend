package by.hackaton.bookcrossing.dto.response;

import lombok.Data;

@Data
public class AccountProfileResponse {
    public Long id;
    public String username;
    public Double longitude;
    public Double latitude;
    public String contact;
    public byte[] avatar;
}
