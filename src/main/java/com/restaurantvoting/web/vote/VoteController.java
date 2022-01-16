package com.restaurantvoting.web.vote;

import com.restaurantvoting.model.Vote;
import com.restaurantvoting.repository.RestaurantRepository;
import com.restaurantvoting.repository.VoteRepository;
import com.restaurantvoting.web.AuthUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.restaurantvoting.util.validation.ValidationUtil.*;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Tag(name = "Vote Controller")
public class VoteController {
    static final String REST_URL = "/api/restaurants/";

    private final VoteRepository repository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    @PostMapping(value = "/vote/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@AuthenticationPrincipal AuthUser authUser, @Param("restaurant_id") int restaurant_id) {
        log.info("user {} voted restaurant {}", authUser.id(), restaurant_id);
        Optional<Vote> optionalVote = repository.findByDateAndUserId(
                LocalDateTime.now().toLocalDate(), authUser.id());
        Vote vote = new Vote(LocalDateTime.now().toLocalDate(),
                LocalDateTime.now().toLocalTime(),
                restaurantRepository.getById(restaurant_id),
                authUser.getUser());
        if (optionalVote.isPresent()) {
            update(authUser, vote, optionalVote.get().getId());
        } else {
            repository.save(vote);
        }

    }

    @PutMapping(value = "/vote/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Vote vote, @PathVariable int id) {
        log.info("user {} update its vote {}", authUser.id(), id);
        repository.checkBelong(id, authUser.id());
        checkToday(vote);
        checkRevote(vote);
        assureIdConsistent(vote, id);
        repository.save(vote);
    }
}
