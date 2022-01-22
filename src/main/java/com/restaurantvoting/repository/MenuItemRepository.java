package com.restaurantvoting.repository;

import com.restaurantvoting.model.MenuItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuItemRepository extends BaseRepository<MenuItem> {
    @Query("SELECT d FROM MenuItem d WHERE d.restaurant.id = :restaurantId")
    List<MenuItem> findAllByRestaurantId(int restaurantId);

    List<MenuItem> findAllByRestaurantIdAndOfferDate(int restaurantId, LocalDate dateTime);
}
