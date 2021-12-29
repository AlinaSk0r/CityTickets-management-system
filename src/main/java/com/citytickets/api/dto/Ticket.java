package com.citytickets.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public final class Ticket {

    private long id;

    private long event_id;

    private String event;

    private Date date;

    private Time time;

    private BigDecimal price;

    private long amount;

    private BigDecimal total;

    private long place_id;

    private String place;

    private long customer_id;

    private String customer;
}

