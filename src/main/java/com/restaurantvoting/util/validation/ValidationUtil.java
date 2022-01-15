package com.restaurantvoting.util.validation;

import com.restaurantvoting.HasId;
import com.restaurantvoting.error.IllegalRequestDataException;
import com.restaurantvoting.model.Vote;
import lombok.experimental.UtilityClass;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@UtilityClass
public class ValidationUtil {

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

    public static void checkRevote(Vote vote) {
        if (vote.getDateTime().isAfter(LocalDateTime.now().toLocalDate().atTime(11, 0))) {
            throw new IllegalRequestDataException("Change vote after 11:00 is not allowed");
        }
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}
