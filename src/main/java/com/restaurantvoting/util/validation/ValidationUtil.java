package com.restaurantvoting.util.validation;

import com.restaurantvoting.HasId;
import com.restaurantvoting.error.IllegalRequestDataException;
import com.restaurantvoting.model.Restaurant;
import com.restaurantvoting.model.Vote;
import lombok.experimental.UtilityClass;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@UtilityClass
public class ValidationUtil {
    public static final LocalTime REVOTE_TIME_LIMIT = LocalTime.of(11, 0);

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void checkModification(int count, int id) {
        if (count == 0) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
    }

    public static void checkVoteTime(LocalTime voteTime) {
        if (voteTime.isAfter(REVOTE_TIME_LIMIT)) {
            throw new IllegalRequestDataException("Change vote after " + REVOTE_TIME_LIMIT + " is not allowed");
        }
    }

    public static void checkVoteDate(LocalDate voteDate) {
        if (!voteDate.isEqual(LocalDate.now())) {
            throw new IllegalRequestDataException("Change archive vote is not allowed");
        }
    }

    public static void checkFirstVote(int userId, Optional<Vote> optionalVote) {
        if (optionalVote.isPresent()) {
            throw new IllegalRequestDataException("User " + userId + " already voted today");
        }
    }

    public static void checkRestaurantExist(int restaurantId, Optional<Restaurant> optionalRestaurant) {
        if (optionalRestaurant.isEmpty()) {
            throw new IllegalRequestDataException("Restaurant " + restaurantId + " does not exist");
        }
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}
