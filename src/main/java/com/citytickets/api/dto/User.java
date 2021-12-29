package com.citytickets.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.citytickets.repo.model.User.UserType;

import javax.validation.constraints.NotBlank;

import java.sql.Date;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public final class User {

    private long id;

    private String username;

    private UserType user_type;

    @NotBlank
    private String first_name;

    @NotBlank
    private String last_name;

    @NotBlank
    private Date birth_date;

    @NotBlank
    private String email;
}

