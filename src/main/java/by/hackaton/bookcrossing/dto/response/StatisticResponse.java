package by.hackaton.bookcrossing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatisticResponse {
    private int bookAmount;
    private int accountAmount;
    private int orderAmount;
}
