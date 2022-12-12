package by.hackaton.bookcrossing.controller;

import by.hackaton.bookcrossing.dto.response.StatisticResponse;
import by.hackaton.bookcrossing.service.StatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/statistics")
public class StatisticController {

    private StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping
    public ResponseEntity<StatisticResponse> getStatistic(){
        return ok(statisticService.getStatistic());
    }
}
