package com.citytickets.service;

import com.citytickets.repo.UserRepo;
import com.citytickets.repo.model.User;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class UserService {

    private final UserRepo userRepo;

    public @NotNull List<User> fetchAll(){
        return userRepo.findAll();
    }

    public @NotNull User fetchById(long id)throws IllegalArgumentException{
        final Optional<User> maybeUser = userRepo.findById(id);

        if (maybeUser.isEmpty()) throw new IllegalArgumentException("User not found");
        else return maybeUser.get();
    }

    public long create(String username, User.UserType user_type, String first_name, String last_name, Date birth_date, String email){
        final User user = new User(username, user_type, first_name, last_name, birth_date, email);
        final User savedUser = userRepo.save(user);

        return savedUser.getId();
    }

    public void update(Long id, String username, User.UserType user_type, String first_name, String last_name, Date birth_date, String email) throws IllegalArgumentException{
        final Optional<User> maybeUser = userRepo.findById(id);
        if (maybeUser.isEmpty()) throw new IllegalArgumentException("User not found");
        final User user = maybeUser.get();
        if (username != null && !username.isBlank()) user.setUsername(username);
        if (user_type != null) user.setUser_type(user_type);
        if (first_name != null && !first_name.isBlank()) user.setFirst_name(first_name);
        if (last_name != null && !last_name.isBlank()) user.setLast_name(last_name);
        if (birth_date != null) user.setBirth_date(birth_date);
        if (email != null && !email.isBlank()) user.setEmail(email);

        userRepo.save(user);
    }

    public void delete(long id)throws IllegalArgumentException{
        userRepo.deleteById(id);
    }
}
