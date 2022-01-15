package com.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@EqualsAndHashCode(callSuper = true)
public class DishTo extends NamedTo {
    @Range(min = 1, max = 500)
    int price;

    @NotNull
    LocalDateTime createDate;

    public DishTo(Integer id, String name, int price, LocalDateTime createDate) {
        super(id, name);
        this.price = price;
        this.createDate = createDate;
    }
}
