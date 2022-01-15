package com.restaurantvoting.web.vote;

import com.restaurantvoting.model.Vote;
import com.restaurantvoting.repository.RestaurantRepository;
import com.restaurantvoting.repository.VoteRepository;
import com.restaurantvoting.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.restaurantvoting.util.validation.ValidationUtil.checkRevote;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {
    static final String REST_URL = "/api/restaurants/";

    private final VoteRepository repository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    @PostMapping(value = "/vote/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("user {} voted {}", authUser.id(), id);
        Optional<Vote> optionalVote = repository.findByDateTimeAfterAndUserId(
                LocalDateTime.now().toLocalDate().atStartOfDay(), authUser.id());
        Vote vote = new Vote (LocalDateTime.now(), restaurantRepository.getById(id), authUser.getUser());
        if (optionalVote.isPresent()) {
            checkRevote(vote);
            repository.delete(optionalVote.get());
        }
        repository.save(vote);
    }

}
