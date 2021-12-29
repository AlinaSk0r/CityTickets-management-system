package com.citytickets.repo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "places")
public final class Place {


    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @NotBlank(message = "Place type cannot be empty")
    @Column(nullable = false, unique = true)
    private String place_type;

    @NotBlank(message = "Address cannot be empty")
    @Column(nullable = false, unique = true)
    private String address;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the name")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the description")
    private String description;

    @Column(nullable = false)
    private Time opens;

    @Column(nullable = false)
    private Time closes;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the path to the photo")
    private String photo;

    public Place() {
    }

    public Place(String place_type, String address, String name, String description, Time opens, Time closes, String photo) throws IllegalArgumentException{
        this.place_type = place_type;
        this.address = address;
        this.name = name;
        this.description = description;
        this.opens = opens;
        this.closes = closes;
        this.photo = photo;
    }

}
