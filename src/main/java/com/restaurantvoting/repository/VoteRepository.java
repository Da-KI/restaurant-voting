package com.restaurantvoting.repository;

import com.restaurantvoting.model.Vote;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional(readOnly = true)
@Tag(name = "Vote Controller")
public interface VoteRepository extends BaseRepository<Vote> {

    Optional<Vote> findByDateTimeAfterAndUserId(LocalDateTime dateTime, int userId);
}
