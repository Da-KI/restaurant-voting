package com.restaurantvoting.web.vote;

import com.restaurantvoting.model.Restaurant;
import com.restaurantvoting.model.Vote;
import com.restaurantvoting.repository.RestaurantRepository;
import com.restaurantvoting.repository.VoteRepository;
import com.restaurantvoting.to.VoteTo;
import com.restaurantvoting.web.AuthUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.restaurantvoting.util.validation.ValidationUtil.*;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Tag(name = "Vote Controller")
public class VoteController {
    static final String REST_URL = "/api/votes/";

    private final VoteRepository repository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    public Vote get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get vote {} for user {}", id, authUser.id());
        return repository.checkBelong(id, authUser.id());
    }

    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll for user {}", authUser.id());
        return repository.findAllByUserId(authUser.id())
                .stream()
                .map(vote -> new VoteTo(vote.getId(), vote.getDate(), vote.getTime(), vote.getRestaurant().getId(), vote.getUser().getId()))
                .collect(Collectors.toList());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Vote> create(@AuthenticationPrincipal AuthUser authUser, @Param("restaurantId") int restaurantId) {
        log.info("user {} voted restaurant {}", authUser.id(), restaurantId);
        Optional<Vote> optionalVote = repository.findByUserIdAndDate(LocalDate.now(), authUser.id());
        checkFirstVote(authUser.id(), optionalVote);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        checkRestaurantExist(restaurantId, optionalRestaurant);
        Vote vote = new Vote(LocalDate.now(),
                LocalTime.now(),
                optionalRestaurant.get(),
                authUser.getUser());
        repository.save(vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(vote.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(vote);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id, @Param("restaurantId") int restaurantId) {
        log.info("user {} update its vote {}", authUser.id(), id);
        LocalTime revoteTime = LocalTime.now();
        checkVoteTime(revoteTime);
        Vote vote = repository.checkBelong(id, authUser.id());
        checkVoteDate(vote.getDate());
        vote.setTime(revoteTime);
        vote.setRestaurant(restaurantRepository.getById(restaurantId));
        repository.save(vote);
    }
}
