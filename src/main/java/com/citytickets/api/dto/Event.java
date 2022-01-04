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

    @NotBlank
    private String name;

    private BigDecimal price;

    private Date date;

    private Time time;

    private long place_id;

    @NotBlank
    private String place;
}
