package by.hackaton.bookcrossing.service;

import by.hackaton.bookcrossing.dto.EventDto;
import by.hackaton.bookcrossing.entity.Event;
import by.hackaton.bookcrossing.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class EventService {

    private EventRepository eventRepository;
    private ModelMapper modelMapper;

    public EventService(EventRepository eventRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
    }

    public List<EventDto> getEvents() {
        return eventRepository.findAll().stream().map(b -> modelMapper.map(b, EventDto.class)).collect(toList());
    }

    public List<EventDto> getEventsByDate(LocalDate date) {
        return eventRepository.findByDate(date).stream().map(b -> modelMapper.map(b, EventDto.class)).collect(toList());
    }

    public void createEvent(EventDto dto) {
        Event organization = modelMapper.map(dto, Event.class);
        eventRepository.save(organization);
    }

}
