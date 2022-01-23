package com.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "menu_item", uniqueConstraints = {@UniqueConstraint(columnNames = {"offer_date", "restaurant_id", "name"}, name = "menu_item_unique_date_restaurant_name_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"restaurant"})
public class MenuItem extends NamedEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference
    private Restaurant restaurant;

    @Column(name = "offer_date", nullable = false)
    @NotNull
    private LocalDate offerDate;

    @Column(name = "price", nullable = false)
    @NotNull
    private int price;

    public MenuItem(Integer id, String name, Restaurant restaurant, LocalDate offerDate, int price) {
        super(id, name);
        this.restaurant = restaurant;
        this.offerDate = offerDate;
        this.price = price;
    }
}
