package com.ecwid.deepcopy.deep_copy;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.Set;

public final class ImmutableTypes {

    private static final Set<Class<?>> IMMUTABLE = Set.of(
            String.class,
            Boolean.class,
            Byte.class,
            Short.class,
            Integer.class,
            Long.class,
            Float.class,
            Double.class,
            Character.class,
            BigInteger.class,
            BigDecimal.class,
            LocalDate.class,
            LocalDateTime.class,
            LocalTime.class,
            Instant.class,
            Duration.class,
            Period.class
    );

    public static boolean isImmutable(Class<?> clazz) {
        if (clazz == null) return false;

        return clazz.isPrimitive() || IMMUTABLE.contains(clazz);
    }

    private ImmutableTypes() {
    }
}
