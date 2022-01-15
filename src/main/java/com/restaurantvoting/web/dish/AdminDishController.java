package com.restaurantvoting.web.dish;

import com.restaurantvoting.model.Dish;
import com.restaurantvoting.model.Restaurant;
import com.restaurantvoting.repository.DishRepository;
import com.restaurantvoting.repository.RestaurantRepository;
import com.restaurantvoting.to.DishTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static com.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminDishController {
    static final String REST_URL = "/api/admin/restaurants/";

    private final DishRepository repository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/dishes")
    public List<Dish> getAll () {
        log.info("get all");
        return repository.findAll();
    }

    @Transactional
    @GetMapping("/{id}/dishes")
    public List<Dish> getDishes (@PathVariable int id) {
        log.info("get dishes for restaurant {}", id);
        System.out.println(repository.findAllByRestaurantId(id));
        return repository.findAllByRestaurantId(id);
    }

    @DeleteMapping("/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    @PostMapping(value = "{id}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@Valid @RequestBody DishTo dishTo, @PathVariable int id) {
        log.info("create {}", dishTo);
        checkNew(dishTo);
        Dish newDish = new Dish(dishTo.getId(), dishTo.getName(), restaurantRepository.getById(id), dishTo.getCreateDate(), dishTo.getPrice());
        repository.save(newDish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newDish.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newDish);
    }

    @PutMapping(value = "/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id) {
        log.info("update {} with id={}", dish, id);
        assureIdConsistent(dish, id);
        repository.save(dish);
    }


}
