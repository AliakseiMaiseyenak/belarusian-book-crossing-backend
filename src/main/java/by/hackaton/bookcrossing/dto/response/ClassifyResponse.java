package by.hackaton.bookcrossing.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClassifyResponse {

    private String response;
    private List<Work> works;

    class Work {
        String title;
        String author;
    }
}
