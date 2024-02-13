package services;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class RandomUtils {
    private static final Random random = new Random();
    private RandomUtils(){}

    public static int getRandomNumber(int lenght){
        return random.nextInt(0, lenght);
    }

    public static String getRandomString(int lenght){
        return RandomStringUtils.randomAlphabetic(lenght);
    }

    public static <T extends Enum<T>> T getRandomEnum(Class<T> classe) {
        int random = getRandomNumber(classe.getEnumConstants().length);
        return classe.getEnumConstants()[random];
    }

    public static double getRandomDouble(int max) {
        return random.nextDouble(0, max);
    }

    public static boolean getRandomBoolean() {
        return random.nextBoolean();
    }

    public static Float getRandomFloat(int max) {
        return random.nextFloat(max);
    }
}
