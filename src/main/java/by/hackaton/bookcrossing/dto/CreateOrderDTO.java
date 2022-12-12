package by.hackaton.bookcrossing.dto;

import by.hackaton.bookcrossing.entity.enums.SendMethod;
import by.hackaton.bookcrossing.entity.enums.SendType;
import lombok.Data;

@Data
public class CreateOrderDTO {
    private Long bookId;
    private String receiver;
    private SendType sendType;
    private SendMethod sendMethod;

}
