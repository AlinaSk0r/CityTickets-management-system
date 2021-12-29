package com.citytickets.service;

import com.citytickets.api.dto.Event;
import com.citytickets.api.dto.Place;
import com.citytickets.api.dto.User;
import com.citytickets.repo.TicketRepo;
import com.citytickets.repo.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class TicketService {

    private final TicketRepo ticketRepo;
    private final String userURL="http://localhost:8081/users";
    private final String placeURL="http://localhost:8082/places";
    private final String eventURL="http://localhost:8083/events";

    public List<Ticket> fetchAll(){
        return ticketRepo.findAll();
    }

    public Ticket fetchById(long id)throws IllegalArgumentException{
        final Optional<Ticket> maybeTicket = ticketRepo.findById(id);

        if (maybeTicket.isEmpty()) throw new IllegalArgumentException("User not found");
        else return maybeTicket.get();
    }

    public long create(long event_id, long amount, long place_id, long customer_id){
        RestTemplate restTemplate=new RestTemplate() ;
        User user=restTemplate.getForObject(userURL+"/"+customer_id, User.class);
        Place place=restTemplate.getForObject(placeURL+"/"+place_id, Place.class);
        Event event=restTemplate.getForObject(eventURL+"/"+event_id, Event.class);
        final Ticket ticket = new Ticket(event.getName(), event.getDate(), event.getTime(), event.getPrice(), amount, event.getPrice().multiply(BigDecimal.valueOf(amount)), place.getName(), user.getUsername());
        final Ticket savedTicket = ticketRepo.save(ticket);

        return savedTicket.getId();
    }

    public void update(Long id, long event_id, long amount, long place_id, long customer_id) throws IllegalArgumentException{
        final Optional<Ticket> maybeTicket = ticketRepo.findById(id);
        if (maybeTicket.isEmpty()) throw new IllegalArgumentException("Ticket not found");
        final Ticket ticket = maybeTicket.get();
        RestTemplate restTemplate=new RestTemplate() ;
        User user=restTemplate.getForObject(userURL+"/"+customer_id, User.class);
        Place place=restTemplate.getForObject(placeURL+"/"+place_id, Place.class);
        Event event=restTemplate.getForObject(eventURL+"/"+event_id, Event.class);
        if (event.getName() != null && !event.getName().isBlank()) ticket.setEvent(event.getName());
        if (event.getDate() != null) ticket.setDate(event.getDate());
        if (event.getTime() != null) ticket.setTime(event.getTime());
        if (event.getPrice() != null){ ticket.setPrice(event.getPrice()); ticket.setTotal(event.getPrice().multiply(BigDecimal.valueOf(amount)));}
        ticket.setAmount(amount);
        if (place.getName() != null && !place.getName().isBlank()) ticket.setPlace(place.getName());
        if (user.getUsername() != null && !user.getUsername().isBlank()) ticket.setCustomer(user.getUsername());

        ticketRepo.save(ticket);
    }

    public void delete(long id)throws IllegalArgumentException{
        ticketRepo.deleteById(id);
    }
}
