package by.hackaton.bookcrossing.dto;

import lombok.Data;

@Data
public class AccountShortDto {
    private Long id;
    private String username;
    private byte[] avatar;
}
