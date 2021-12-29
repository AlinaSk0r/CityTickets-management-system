package com.citytickets.api;

import com.citytickets.api.dto.Event;
import com.citytickets.service.EventService;
import com.sun.istack.NotNull;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
public final class EventController {

    @Autowired
    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    private final EventService eventService;

    @Contract("_ -> new")
    private @NotNull
    Event eventToDto(@NotNull @org.jetbrains.annotations.NotNull com.citytickets.repo.model.Event eventModel){
        return  Event.builder()
                .id(eventModel.getId())
                .name(eventModel.getName())
                .place(eventModel.getPlace())
                .price(eventModel.getPrice())
                .description(eventModel.getDescription())
                .status(eventModel.getStatus())
                .date(eventModel.getDate())
                .time(eventModel.getTime())
                .photo(eventModel.getPhoto())
                .organizer(eventModel.getOrganizer())
                .build();
    }

    @GetMapping
    public @NotNull
    @org.jetbrains.annotations.NotNull
    ResponseEntity<List<Event>> index(){
        final List<Event> events = eventService.fetchAll().stream().map(this::eventToDto).collect(Collectors.toList());
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> show(@PathVariable long id){
        try {
            final Event event = eventToDto(eventService.fetchById(id));
            return ResponseEntity.ok(event);
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @PostMapping
    public @NotNull
    @org.jetbrains.annotations.NotNull ResponseEntity<Void> create(@RequestBody @NotNull @org.jetbrains.annotations.NotNull Event event){
        final String name = event.getName();
        final long place_id= event.getPlace_id();
        final BigDecimal price = event.getPrice();
        final String description = event.getDescription();
        final com.citytickets.repo.model.Event.Status status = event.getStatus();
        final Date date = event.getDate();
        final Time time = event.getTime();
        final String photo = event.getPhoto();
        final long organizer_id= event.getOrganizer_id();
        final long id = eventService.create(name, place_id, price, description, status, date, time, photo, organizer_id);
        final String location = String.format("/events/%d",id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public @NotNull
    @org.jetbrains.annotations.NotNull ResponseEntity<Void> update(@PathVariable long id, @RequestBody @NotNull @org.jetbrains.annotations.NotNull Event event){
        final String name = event.getName();
        final long place_id= event.getPlace_id();
        final BigDecimal price = event.getPrice();
        final String description = event.getDescription();
        final com.citytickets.repo.model.Event.Status status = event.getStatus();
        final Date date = event.getDate();
        final Time time = event.getTime();
        final String photo = event.getPhoto();
        final long organizer_id= event.getOrganizer_id();

        try {
            eventService.update(id, name,  place_id, price, description, status, date, time, photo, organizer_id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public @NotNull
    @org.jetbrains.annotations.NotNull ResponseEntity<Void> delete(@PathVariable long id){
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
