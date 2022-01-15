package com.restaurantvoting.repository;

import com.restaurantvoting.model.Restaurant;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Tag(name = "Restaurants Controller")
public interface RestaurantRepository extends BaseRepository<Restaurant> {
}
