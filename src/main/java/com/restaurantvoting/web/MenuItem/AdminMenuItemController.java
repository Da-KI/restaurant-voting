package com.restaurantvoting.web.MenuItem;

import com.restaurantvoting.model.MenuItem;
import com.restaurantvoting.repository.MenuItemRepository;
import com.restaurantvoting.repository.RestaurantRepository;
import com.restaurantvoting.to.MenuItemTo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
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

    @GetMapping("/menu_items")
    public Page<MenuItem> getAll(@RequestParam(defaultValue = "0", required = false) Integer pageNo,
                                 @RequestParam(defaultValue = "20", required = false) Integer pageSize,
                                 @RequestParam(defaultValue = "offerDate", required = false) String sortBy,
                                 @RequestParam(defaultValue = "DESC", required = false) String sortDir) {
        log.info("get all page {}, page size {}, sort by {} direction {}", pageNo, pageSize, sortBy, sortDir);
        return repository.findAll(PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDir), sortBy)));
    }

    @GetMapping("/{restaurantId}/menu_items")
    public List<MenuItem> getByRestaurantId(@PathVariable int restaurantId) {
        log.info("get MenuItems for restaurant {}", restaurantId);
        return repository.findAllByRestaurantId(restaurantId);
    }

    @GetMapping("/{restaurantId}/menu_items-by-date")
    public List<MenuItem> getByRestaurantIdAndDate(@PathVariable int restaurantId,
                                                   @RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate offerDate) {
        log.info("get menuItems for restaurant {} with date {}", restaurantId, offerDate);
        return repository.findAllByRestaurantIdAndOfferDate(restaurantId, offerDate);
    }

    @GetMapping("/{restaurantId}/menu_items/{id}")
    public ResponseEntity<MenuItem> get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get {} for restaurant {}", id, restaurantId);
        repository.checkBelong(id, restaurantId);
        return ResponseEntity.of(repository.get(id, restaurantId));
    }

    @DeleteMapping("/{restaurantId}/menu_items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete {} for restaurant {}", id, restaurantId);
        MenuItem menuItem = repository.checkBelong(id, restaurantId);
        repository.delete(menuItem);
    }

    @PostMapping(value = "/{restaurantId}/menu_items", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> create(@Valid @RequestBody MenuItemTo menuItemTo, @PathVariable int restaurantId) {
        log.info("create {}", menuItemTo);
        checkNew(menuItemTo);
        MenuItem newMenuItem = new MenuItem(menuItemTo.getId(), menuItemTo.getName(), restaurantRepository.getById(restaurantId), menuItemTo.getOfferDate(), menuItemTo.getPrice());
        repository.save(newMenuItem);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newMenuItem.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newMenuItem);
    }

    @PutMapping(value = "/{restaurantId}/menu_items/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuItemTo menuItemTo, @PathVariable int id, @PathVariable int restaurantId) {
        log.info("update {} with id={}", menuItemTo, id);
        assureIdConsistent(menuItemTo, id);
        MenuItem newMenuItem = repository.checkBelong(id, restaurantId);
        newMenuItem.setName(menuItemTo.getName());
        newMenuItem.setOfferDate(menuItemTo.getOfferDate());
        newMenuItem.setPrice(menuItemTo.getPrice());
        repository.save(newMenuItem);
    }
}
