package fr.vbillard.tissusdeprincesseboot.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

import com.github.javafaker.Faker;

public class RandomUtils {
    public static Faker FAKER = new Faker(Locale.FRANCE);
    private static final SecureRandom RANDOM = new SecureRandom();

    public static <T> T eventually(final Supplier<T> supplier) {
        return org.apache.commons.lang3.RandomUtils.nextBoolean() ? null : supplier.get();
    }
/*
    public static LocalDate futureDate(int atMost, TimeUnit unit) {
        return LocalDate.ofInstant(FAKER.date().future(atMost, unit).toInstant(), ZoneId.systemDefault());
    }

 */

    public static LocalDateTime futureDateTime(int atMost, TimeUnit unit) {
        return LocalDateTime.ofInstant(FAKER.date().future(atMost, unit).toInstant(), ZoneId.systemDefault());
    }

    public static OffsetDateTime futureOffsetDateTime(int atMost, TimeUnit unit) {
        return OffsetDateTime.ofInstant(FAKER.date().future(atMost, unit).toInstant(), ZoneId.systemDefault());
    }

    public static int nextInt(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }
/*
    public static LocalDate pastDate(int atMost, TimeUnit unit) {
        return LocalDate.ofInstant(FAKER.date().past(atMost, unit).toInstant(), ZoneId.systemDefault());
    }

 */

    public static LocalDateTime pastDateTime(int atMost, TimeUnit unit) {
        return LocalDateTime.ofInstant(FAKER.date().past(atMost, unit).toInstant(), ZoneId.systemDefault());
    }

    public static OffsetDateTime pastOffsetDateTime(int atMost, TimeUnit unit) {
        return OffsetDateTime.ofInstant(FAKER.date().past(atMost, unit).toInstant(), ZoneId.systemDefault());
    }

    public static ZonedDateTime pastZonedDateTime(int atMost, TimeUnit unit) {
        return ZonedDateTime.ofInstant(FAKER.date().past(atMost, unit).toInstant(), ZoneId.systemDefault());
    }

    @SafeVarargs
    public static <T> T randomInList(T... elements) {
        int x = RANDOM.nextInt(elements.length);
        return elements[x];
    }


    public static <T> List<T> randomList(Function<Integer, T> factory) {
        return randomList(factory, 10);
    }

    public static <T> List<T> randomList(Supplier<T> factory) {
        return randomList(index -> factory.get(), 10);
    }

    public static <T> List<T> randomList(final Function<Integer, T> factory, final int maxItems) {
        return randomList(factory, 0, maxItems);
    }

    public static <T> List<T> randomList(final Supplier<T> factory, final int maxItems) {
        return randomList(index -> factory.get(), 0, maxItems);
    }

    public static <T> List<T> randomList(final Supplier<T> factory, final int minItems, final int maxItems) {
        return randomList(index -> factory.get(), minItems, maxItems);
    }

    public static <T> List<T> randomList(final Function<Integer, T> factory, final int minItems, final int maxItems) {
        int x = nextInt(minItems, maxItems);
        List list = new ArrayList<T>();

        for (int i = 0; i < x; i++) {
            list.add(factory.apply(i));
        }
        return list;
    }

    public static <K, V> Map<K, V> randomMap(final Supplier<K> keyFactory, final Supplier<V> valueFactory) {
        return randomMap(keyFactory, valueFactory, 0, 10);
    }

    public static <K, V> Map<K, V> randomMap(final Supplier<K> keyFactory, final Supplier<V> valueFactory, final int minItems, final int maxItems) {
        int x = nextInt(minItems, maxItems);
        Map map = new HashMap<K, V>();

        for (int i = 0; i < x; i++) {
            map.put(keyFactory.get(), valueFactory.get());
        }
        return map;
    }

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = RANDOM.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
