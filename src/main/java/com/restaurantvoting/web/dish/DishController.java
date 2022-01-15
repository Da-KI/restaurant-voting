package com.restaurantvoting.web.dish;

import com.restaurantvoting.model.Dish;
import com.restaurantvoting.repository.DishRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class DishController {
    static final String REST_URL = "/api/restaurants/";

    private final DishRepository repository;

    @Transactional
    @GetMapping("/{id}/dishes")
    public List<Dish> getAll(@PathVariable int id) {
        log.info("get dishes for restaurant {}", id);
        return repository.findAllByRestaurantId(id);
    }

    @GetMapping("/{id}/today-dishes/")
    public List<Dish> getToday(@PathVariable int id) {
        log.info("get dishes for restaurant {}", id);
        return repository.findAllByRestaurantIdAndCreateDateAfter(id, LocalDateTime.now().toLocalDate().atStartOfDay());
    }

}
