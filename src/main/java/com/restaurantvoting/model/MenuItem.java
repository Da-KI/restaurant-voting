package com.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "menu_item")
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

    @Column(name = "create_date", nullable = false)
    @NotNull
    private LocalDateTime createDate;

    @Column(name = "price", nullable = false)
    @NotNull
    private int price;

    public MenuItem(Integer id, String name, Restaurant restaurant, LocalDateTime createDate, int price) {
        super(id, name);
        this.restaurant = restaurant;
        this.createDate = createDate;
        this.price = price;
    }
}
