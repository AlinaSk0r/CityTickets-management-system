package com.citytickets.repo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
public final class User {

    public enum UserType{
        Admin, Manager, Customer, Organizer, Accountant
    }

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @NotBlank(message = "Username cannot be empty")
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private UserType user_type;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the first name")
    private String first_name;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the last name")
    private String last_name;

    @Column(nullable = false)
    private Date birth_date;

    @Column(nullable = false)
    @NotBlank(message = "Please, write in the email")
    private String email;

    public User() {
    }

    public User(String username, UserType user_type, String first_name, String last_name, Date birth_date, String email) {
        this.username = username;
        this.user_type = user_type;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.email = email;
    }
}
