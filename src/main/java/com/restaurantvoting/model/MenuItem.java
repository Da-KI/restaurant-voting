package com.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "menu_item", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "offer_date", "name"}, name = "menu_item_unique_restaurant_date_name_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
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
