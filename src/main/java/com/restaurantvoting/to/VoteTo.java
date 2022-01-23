package com.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Value
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {
    @NotNull
    LocalDate date;

    @NotNull
    LocalTime time;

    @NotNull
    Integer restaurantId;

    @NotNull
    Integer userId;

    public VoteTo(Integer id, LocalDate date, LocalTime time, Integer restaurantId, Integer userId) {
        super(id);
        this.date = date;
        this.time = time;
        this.restaurantId = restaurantId;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "VoteTo:" + id + '[' + date + time + ']';
    }
}
