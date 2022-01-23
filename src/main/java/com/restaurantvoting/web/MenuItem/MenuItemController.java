package com.restaurantvoting.web.MenuItem;

import com.restaurantvoting.model.MenuItem;
import com.restaurantvoting.repository.MenuItemRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = MenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Tag(name = "MenuItem Controller")
public class MenuItemController {
    static final String REST_URL = "/api/restaurants/";

    private final MenuItemRepository repository;

    @GetMapping("/today-menu_items/")
    public List<MenuItem> getAllToday() {
        log.info("get today menuItems");
        return repository.findAllByOfferDateOrderByRestaurantId(LocalDate.now());
    }

    @GetMapping("/{id}/today-menu_items/")
    public List<MenuItem> getToday(@PathVariable int id) {
        log.info("get today menuItems for restaurant {}", id);
        return repository.findAllByRestaurantIdAndOfferDate(id, LocalDate.now());
    }
}
