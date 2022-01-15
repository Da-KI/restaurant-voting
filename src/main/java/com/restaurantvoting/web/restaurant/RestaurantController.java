package com.restaurantvoting.web.restaurant;

import com.restaurantvoting.to.RestaurantTo;
import com.restaurantvoting.web.AuthUser;
import com.restaurantvoting.model.Restaurant;
import com.restaurantvoting.repository.RestaurantRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
//@Tag(name = "User Restaurants Controller")
public class RestaurantController {
    static final String REST_URL = "/api/restaurants/";

    private final RestaurantRepository repository;

    @Transactional
    @GetMapping
    public List<Restaurant> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll for user {}", authUser.id());
        return repository.findAll();
    }


}
