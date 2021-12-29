package com.citytickets.api;

import com.citytickets.repo.model.User;
import com.citytickets.service.UserService;
import com.sun.istack.NotNull;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public final class UserController {

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    private final UserService userService;

    @Contract("_ -> new")
    private @NotNull
    com.citytickets.api.dto.User userToDto(@NotNull @org.jetbrains.annotations.NotNull User userModel){
        return  com.citytickets.api.dto.User.builder()
                .id(userModel.getId())
                .username(userModel.getUsername())
                .user_type(userModel.getUser_type())
                .first_name(userModel.getFirst_name())
                .last_name(userModel.getLast_name())
                .birth_date(userModel.getBirth_date())
                .email(userModel.getEmail())
                .build();
    }

    @GetMapping
    public @NotNull
    @org.jetbrains.annotations.NotNull
    ResponseEntity<List<com.citytickets.api.dto.User>> index(){
        final List<com.citytickets.api.dto.User> users = userService.fetchAll().stream().map(this::userToDto).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.citytickets.api.dto.User> show(@PathVariable long id){
        try {
            final com.citytickets.api.dto.User user = userToDto(userService.fetchById(id));
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @PostMapping
    public @NotNull
    @org.jetbrains.annotations.NotNull ResponseEntity<Void> create(@RequestBody @NotNull @org.jetbrains.annotations.NotNull com.citytickets.api.dto.User user){

        final String username = user.getUsername();
        final User.UserType user_type = user.getUser_type();
        final String first_name = user.getFirst_name();
        final String last_name = user.getLast_name();
        final Date birth_date = user.getBirth_date();
        final String email = user.getEmail();
        final long id = userService.create(username, user_type, first_name, last_name, birth_date, email);
        final String location = String.format("/users/%d",id);

        return ResponseEntity.created(URI.create(location)).build();
    }

    @PatchMapping("/{id}")
    public @NotNull
    @org.jetbrains.annotations.NotNull ResponseEntity<Void> update(@PathVariable long id, @RequestBody @NotNull @org.jetbrains.annotations.NotNull com.citytickets.api.dto.User user){

        final String username = user.getUsername();
        final User.UserType user_type = user.getUser_type();
        final String first_name = user.getFirst_name();
        final String last_name = user.getLast_name();
        final Date birth_date = user.getBirth_date();
        final String email = user.getEmail();

        try {
            userService.update(id, username, user_type, first_name, last_name, birth_date, email);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){ return ResponseEntity.notFound().build(); }
    }

    @DeleteMapping("/{id}")
    public @NotNull
    @org.jetbrains.annotations.NotNull ResponseEntity<Void> delete(@PathVariable long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
