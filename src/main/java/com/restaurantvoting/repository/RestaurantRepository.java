package com.restaurantvoting.repository;

import com.restaurantvoting.model.Restaurant;
import com.restaurantvoting.to.RestaurantTo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Transactional(readOnly = true)
@Tag(name = "Restaurants Controller")
public interface RestaurantRepository extends BaseRepository<Restaurant> {

//    @Query("SELECT r FROM Restaurant r WHERE r.dishes = LOWER(:email)")
//     Optional<Restaurant> findBy (Date after, Date before);
}
