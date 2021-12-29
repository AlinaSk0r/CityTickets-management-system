package com.citytickets.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public final class Event {

    private long id;

    private String place;

    @NotBlank
    private String name;

    private long place_id;

    private BigDecimal price;

    @NotBlank
    private String description;

    private com.citytickets.repo.model.Event.Status status;

    private Date date;

    private Time time;

    @NotBlank
    private String photo;

    private long organizer_id;

    private String organizer;
}

