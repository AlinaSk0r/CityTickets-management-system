package com.citytickets.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import java.sql.Time;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public final class Place {

    private long id;

    private String place_type;

    @NotBlank
    private String address;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private Time opens;

    private Time closes;

    @NotBlank
    private String photo;
}

