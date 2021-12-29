package com.citytickets.service;

import com.citytickets.api.dto.Place;
import com.citytickets.api.dto.User;
import com.citytickets.repo.EventRepo;
import com.citytickets.repo.model.Event;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class EventService {

    private final EventRepo eventRepo;

    private final String userURL="http://localhost:8081/users";
    private final String placeURL="http://localhost:8082/places";

    public @NotNull List<Event> fetchAll(){
        return eventRepo.findAll();
    }

    public @NotNull Event fetchById(long id)throws IllegalArgumentException{
        final Optional<Event> maybeEvent = eventRepo.findById(id);
        if (maybeEvent.isEmpty()) throw new IllegalArgumentException("Event not found");
        else return maybeEvent.get();
    }

    public long create(String name, long place_id, BigDecimal price, String description, Event.Status status, Date date, Time time, String photo, long organizer_id){
        RestTemplate restTemplate=new RestTemplate() ;
        User user=restTemplate.getForObject(userURL+"/"+organizer_id, User.class);
        Place place=restTemplate.getForObject(placeURL+"/"+place_id, Place.class);

        final Event event = new Event(name, place.getName(), price, description, status, date, time, photo, user.getUsername());
        final Event savedEvent = eventRepo.save(event);

        return savedEvent.getId();
    }

    public void update(Long id, String name, long place_id, BigDecimal price, String description, Event.Status status, Date date, Time time, String photo, long organizer_id) throws IllegalArgumentException{
        final Optional<Event> maybeEvent = eventRepo.findById(id);
        if (maybeEvent.isEmpty()) throw new IllegalArgumentException("Event not found");
        final Event event = maybeEvent.get();
        RestTemplate restTemplate=new RestTemplate() ;
        User user=restTemplate.getForObject(userURL+"/"+organizer_id, User.class);
        Place place=restTemplate.getForObject(placeURL+"/"+place_id, Place.class);

        if (name != null && !name.isBlank()) event.setName(name);
        if (place.getName() != null && !place.getName().isBlank()) event.setPlace(place.getName());
        if (price != null) event.setPrice(price);
        if (description != null && !description.isBlank()) event.setDescription(description);
        if (status != null) event.setStatus(status);
        if (date != null) event.setDate(date);
        if (time != null) event.setTime(time);
        if (photo != null && !photo.isBlank()) event.setPhoto(photo);
        if (user.getUsername() != null && !user.getUsername().isBlank()) event.setOrganizer(user.getUsername());
        eventRepo.save(event);
    }

    public void delete(long id)throws IllegalArgumentException{
        eventRepo.deleteById(id);
    }
}
