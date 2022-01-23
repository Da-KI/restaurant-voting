package com.restaurantvoting.repository;

import com.restaurantvoting.error.DataConflictException;
import com.restaurantvoting.model.MenuItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuItemRepository extends BaseRepository<MenuItem> {
    @Query("SELECT d FROM MenuItem d WHERE d.restaurant.id = :restaurantId ORDER BY d.offerDate DESC")
    List<MenuItem> findAllByRestaurantId(int restaurantId);

    @Query("SELECT d FROM MenuItem d WHERE d.offerDate = :offerDate ORDER BY d.restaurant.id DESC")
    List<MenuItem> findAllByOfferDateOrderByRestaurantId(LocalDate offerDate);

    @Query("SELECT d FROM MenuItem d WHERE d.restaurant.id = :restaurantId AND d.offerDate = :offerDate ORDER BY d.name DESC")
    List<MenuItem> findAllByRestaurantIdAndOfferDate(int restaurantId, LocalDate offerDate);

    @Query("SELECT d FROM MenuItem d WHERE d.id = :id and d.restaurant.id = :restaurantId")
    Optional<MenuItem> get(int id, int restaurantId);

    default MenuItem checkBelong(int id, int restaurantId) {
        return get(id, restaurantId).orElseThrow(
                () -> new DataConflictException("MenuItem id=" + id + " doesn't belong to Restaurant id=" + restaurantId));
    }
}
