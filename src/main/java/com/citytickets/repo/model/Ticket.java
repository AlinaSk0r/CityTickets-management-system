package com.citytickets.repo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "tickets")
public final class Ticket {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @NotBlank(message = "Event cannot be empty")
    @Column(nullable = false)
    private String event;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Time time;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private long amount;

    @Column(nullable = false)
    private BigDecimal total;

    @NotBlank(message = "Place cannot be empty")
    @Column(nullable = false)
    private String place;

    @NotBlank(message = "Customer cannot be empty")
    @Column(nullable = false)
    private String customer;

    public Ticket() {
    }

    public Ticket(String event, Date date, Time time, BigDecimal price, long amount, BigDecimal total, String place, String customer) {
        this.event = event;
        this.date = date;
        this.time = time;
        this.price = price;
        this.amount = amount;
        this.total = total;
        this.place = place;
        this.customer = customer;
    }
}
