package utility;

import java.util.concurrent.ThreadLocalRandom;

public class ThreadRandom {
    /**
     * Returns an int value
     * @return random int
     */
    public static int rand() {
        return ThreadLocalRandom.current().nextInt();
    }

    /**
     * Random int value including min but excluding max
     * @param min min value
     * @param max max value
     * @return random int between min and excluding max
     */
    public static int rand(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /**
     * Returns a positive int including 0
     * @return random positive int
     */
    public static int randAbs() {
        return ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
    }

    /**
     * Returns a positive int up to max
     * @param max max value
     * @return random int between 0 and max
     */
    public static int randAbs(int max) {
        return ThreadLocalRandom.current().nextInt(0, max);
    }
}
