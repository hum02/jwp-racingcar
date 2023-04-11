package racingcar.utils;

import java.util.Random;

public class RandomPowerGenerator {
    private final static Random random = new Random();
    private final static int RANDOM_RANGE = 10;

    public static int createRandomPower() {
        return random.nextInt(RANDOM_RANGE);
    }
}