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
@Table(name = "events")
public final class Event {

    public enum Status{
        Active, Unactive
    }

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @NotBlank(message = "Name cannot be empty")
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the place")
    private String place;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the description")
    private String description;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Time time;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the path to the photo")
    private String photo;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the organizer")
    private String organizer;

    public Event() {
    }

    public Event(String name, String place, BigDecimal price, String description, Status status, Date date, Time time, String photo, String organizer) {
        this.name = name;
        this.place = place;
        this.price = price;
        this.description = description;
        this.status = status;
        this.date = date;
        this.time = time;
        this.photo = photo;
        this.organizer = organizer;
    }
}
