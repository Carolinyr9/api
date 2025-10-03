package com.eventostec.api.service;

import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventAddressProjection;
import com.eventostec.api.domain.event.EventDetailsDTO;
import com.eventostec.api.domain.event.EventRequestDTO;
import com.eventostec.api.domain.event.EventResponseDTO;
import com.eventostec.api.exception.EventNotFoundException;
import com.eventostec.api.repositories.EventRepository;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public Event createEvent(EventRequestDTO data){

        Event newEvent = new Event();
        newEvent.setTitle(data.title());
        newEvent.setDescription(data.description());
        newEvent.setEventUrl(data.eventUrl());
        newEvent.setDate(new Date(data.date()));
        newEvent.setImgUrl(data.image());
        
        eventRepository.save(newEvent);
        return newEvent;
    }

    public List<EventResponseDTO> getUpcomingEvents(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<EventAddressProjection> events = this.eventRepository.findUpcomingEvents(new Date(), pageable);
        return events.stream()
                .map(event -> new EventResponseDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getDate(),
                        event.getCity() != null ? event.getCity() : "",
                        event.getUf() != null ? event.getUf() : "",
                        event.getRemote(),
                        event.getEventUrl(),
                        event.getImgUrl())
                )
                .toList();
    }

    public List<EventResponseDTO> getFilteredEvents(int page, int size, String city, String uf, Date startDate, Date endDate){

        city = (city != null) ? city : "";
        uf = (uf != null) ? uf : "";
        startDate = (startDate != null) ? startDate : new Date(0);
        endDate = (endDate != null) ? endDate : new Date();


        Pageable pageable = PageRequest.of(page, size);
        List<EventAddressProjection> events = this.eventRepository.findFilteredEvents(city, uf, startDate, endDate, pageable);
        return events.stream()
                .map(event -> new EventResponseDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getDate(),
                        event.getCity() != null ? event.getCity() : "",
                        event.getUf() != null ? event.getUf() : "",
                        event.getRemote(),
                        event.getEventUrl(),
                        event.getImgUrl())
                )
                .toList();
    }

    public List<EventResponseDTO> getEventByTitle(String title){
        title = (title != null) ? title : "";

        List<EventAddressProjection> eventsList = this.eventRepository.findEventsByTitle(title);

        return eventsList.stream().map(event -> new EventResponseDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getDate(),
                        event.getCity() != null ? event.getCity() : "",
                        event.getUf() != null ? event.getUf() : "",
                        event.getRemote(),
                        event.getEventUrl(),
                        event.getImgUrl())
                )
                .toList();
    }

    public void deleteEvent(UUID id){
        if(existsEventById(id)){
            this.eventRepository.deleteById(null);
        }
    }

    /* public EventDetailsDTO getEventDetails(UUID id){
        existsEventById(id);
        

    } */

    private Boolean existsEventById(UUID id){
        return eventRepository.findById(id)
                        .map(e -> true)
                        .orElseThrow(() -> new EventNotFoundException());
    }
}
