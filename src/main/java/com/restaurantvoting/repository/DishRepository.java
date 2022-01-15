package com.restaurantvoting.repository;

import com.restaurantvoting.model.Dish;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Tag(name = "Dish Controller")
public interface DishRepository extends BaseRepository<Dish> {
    @Query("SELECT d FROM Dish d WHERE d.restaurant.id = :restaurantId")
    List<Dish> findAllByRestaurantId(int restaurantId);

    List<Dish> findAllByRestaurantIdAndCreateDateAfter(int restaurantId, LocalDateTime dateTime);

}
