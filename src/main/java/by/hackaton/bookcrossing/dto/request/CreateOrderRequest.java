package by.hackaton.bookcrossing.dto.request;

import by.hackaton.bookcrossing.entity.enums.Obtain;
import by.hackaton.bookcrossing.entity.enums.SendType;
import lombok.Data;

@Data
public class CreateOrderRequest {
    private Long bookId;
    private String receiver;
    private SendType sendType;
    private Obtain obtain;
}
