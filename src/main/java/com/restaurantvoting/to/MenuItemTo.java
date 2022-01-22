package com.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class MenuItemTo extends NamedTo {
    @NotNull
    int price;

    @NotNull
    LocalDate offerDate;

    public MenuItemTo(Integer id, String name, int price, LocalDate offerDate) {
        super(id, name);
        this.price = price;
        this.offerDate = offerDate;
    }
}
