package com.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.restaurantvoting.HasId;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "dishes")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Dish extends NamedEntity implements HasId {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference
    private Restaurant restaurant;

    @Column(name = "create_date", nullable = false)
    @NotNull
    private LocalDateTime createDate;

    @Column(name = "price")
    @Range(min = 1, max = 500)
    private int price;

    public Dish(Integer id, String name, Restaurant restaurant, LocalDateTime createDate, int price) {
        super(id, name);
        this.restaurant = restaurant;
        this.createDate = createDate;
        this.price = price;
    }
}
