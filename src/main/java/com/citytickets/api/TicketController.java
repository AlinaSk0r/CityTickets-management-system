package com.citytickets.api;

import com.citytickets.api.dto.Ticket;
import com.citytickets.service.TicketService;
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
@RequestMapping("/tickets")
public final class TicketController {

    @Autowired
    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    private final TicketService ticketService;

    @Contract("_ -> new")
    private @NotNull
    Ticket ticketToDto(@NotNull @org.jetbrains.annotations.NotNull com.citytickets.repo.model.Ticket ticketModel){
        return  Ticket.builder()
                .id(ticketModel.getId())
                .event(ticketModel.getEvent())
                .place(ticketModel.getPlace())
                .date(ticketModel.getDate())
                .time(ticketModel.getTime())
                .price(ticketModel.getPrice())
                .amount(ticketModel.getAmount())
                .total(ticketModel.getTotal())
                .customer(ticketModel.getCustomer())
                .build();
    }

    @GetMapping
    public @NotNull
    @org.jetbrains.annotations.NotNull
    ResponseEntity<List<Ticket>> index(){
        final List<Ticket> tickets = ticketService.fetchAll().stream().map(this::ticketToDto).collect(Collectors.toList());
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> show(@PathVariable long id){
        try {
            final Ticket ticket = ticketToDto(ticketService.fetchById(id));
            return ResponseEntity.ok(ticket);
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @PostMapping
    public @NotNull
    @org.jetbrains.annotations.NotNull ResponseEntity<Void> create(@RequestBody @NotNull @org.jetbrains.annotations.NotNull Ticket ticket){
        final long event_id = ticket.getEvent_id();
        final Date date = ticket.getDate();
        final Time time = ticket.getTime();
        final BigDecimal price = ticket.getPrice();
        final long amount = ticket.getAmount();
        final BigDecimal total = ticket.getTotal();
        final long place_id = ticket.getPlace_id();
        final long costumer_id = ticket.getCustomer_id();
        final long id = ticketService.create(event_id, amount, place_id, costumer_id);
        final String location = String.format("/tickets/%d",id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public @NotNull
    @org.jetbrains.annotations.NotNull ResponseEntity<Void> update(@PathVariable long id, @RequestBody @NotNull @org.jetbrains.annotations.NotNull Ticket ticket){
        final long event_id = ticket.getEvent_id();
        final Date date = ticket.getDate();
        final Time time = ticket.getTime();
        final BigDecimal price = ticket.getPrice();
        final long amount = ticket.getAmount();
        final BigDecimal total = ticket.getTotal();
        final long place_id = ticket.getPlace_id();
        final long costumer_id = ticket.getCustomer_id();

        try {
            ticketService.update(id, event_id, amount, place_id, costumer_id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public @NotNull
    @org.jetbrains.annotations.NotNull ResponseEntity<Void> delete(@PathVariable long id){
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
