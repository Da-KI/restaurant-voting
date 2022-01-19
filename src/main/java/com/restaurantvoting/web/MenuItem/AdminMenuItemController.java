package com.restaurantvoting.web.MenuItem;

import com.restaurantvoting.model.MenuItem;
import com.restaurantvoting.repository.MenuItemRepository;
import com.restaurantvoting.repository.RestaurantRepository;
import com.restaurantvoting.to.MenuItemTo;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(value = AdminMenuItemController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Tag(name = "Admin MenuItem Controller")
public class AdminMenuItemController {
    static final String REST_URL = "/api/admin/restaurants/";

    private final MenuItemRepository repository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/dishes")
    public List<MenuItem> getAll() {
        log.info("get all");
        return repository.findAll();
    }

    @Transactional
    @GetMapping("/{id}/dishes")
    public List<MenuItem> getDishes(@PathVariable int id) {
        log.info("get dishes for restaurant {}", id);
        return repository.findAllByRestaurantId(id);
    }

    @DeleteMapping("/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    @PostMapping(value = "{id}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> create(@Valid @RequestBody MenuItemTo menuItemTo, @PathVariable int id) {
        log.info("create {}", menuItemTo);
        checkNew(menuItemTo);
        MenuItem newMenuItem = new MenuItem(menuItemTo.getId(), menuItemTo.getName(), restaurantRepository.getById(id), menuItemTo.getCreateDate(), menuItemTo.getPrice());
        repository.save(newMenuItem);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newMenuItem.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newMenuItem);
    }

    @PutMapping(value = "/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuItem menuItem, @PathVariable int id) {
        log.info("update {} with id={}", menuItem, id);
        assureIdConsistent(menuItem, id);
        repository.save(menuItem);
    }

}
