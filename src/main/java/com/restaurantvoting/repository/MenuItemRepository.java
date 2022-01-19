package com.restaurantvoting.repository;

import com.restaurantvoting.model.MenuItem;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Tag(name = "MenuItem Controller")
public interface MenuItemRepository extends BaseRepository<MenuItem> {
    @Query("SELECT d FROM MenuItem d WHERE d.restaurant.id = :restaurantId")
    List<MenuItem> findAllByRestaurantId(int restaurantId);

    List<MenuItem> findAllByRestaurantIdAndCreateDateAfter(int restaurantId, LocalDateTime dateTime);

}
