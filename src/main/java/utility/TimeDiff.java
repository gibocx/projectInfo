package utility;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class TimeDiff {
    private long startTime = System.nanoTime();
    private static final DecimalFormat format = new DecimalFormat("#.##");

    public TimeDiff() {
    }

    public void reset() {
        this.startTime = System.nanoTime();
    }

    /**
     * @return elapsed time in nanoseconds
     */
    public long getNanos() {
        return System.nanoTime() - this.startTime;
    }

    /**
     * @return elapsed time in microseconds
     */
    public long getMicros() {
        return TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - this.startTime);
    }

    /**
     * @return elapsed time in milliseconds
     */
    public long getMillis() {
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - this.startTime);
    }

    /**
     * @return elapsed time in seconds
     */
    public long getSeconds() {
        return TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - this.startTime);
    }

    /**
     * @return elapsed time in minutes
     */
    public long getMinutes() {
        return TimeUnit.NANOSECONDS.toMinutes(System.nanoTime() - this.startTime);
    }

    /**
     * @return elapsed time in hours
     */
    public long getHours() {
        return TimeUnit.NANOSECONDS.toHours(System.nanoTime() - this.startTime);
    }

    /**
     * @return elapsed time in days
     */
    public long getDays() {
        return TimeUnit.NANOSECONDS.toDays(System.nanoTime() - this.startTime);
    }

    /**
     * Chooses the best format for the elapsed time. Rounds to two decimal places e.g. 1.43 ms
     *
     * @return elapsed time rounded to two places
     */
    public String chooseBest() {
        return chooseBest(getNanos());
    }

    /**
     * Chooses the best format for given time difference. Rounds to two decimal places e.g. 1.43 ms
     *
     * @param diff time difference in nanoseconds
     * @return elapsed time rounded to two places
     */
    public static String chooseBest(long diff) {

        String result;

        if ((diff / Time.ONE_DAY_NS) > 0) {
            result = format.format((float)diff / Time.ONE_DAY_NS) + " days";
        } else if ((diff / Time.ONE_HOUR_NS) > 0) {
            result = format.format((float)diff / Time.ONE_HOUR_NS) + " hours";
        } else if ((diff / Time.ONE_MIN_NS) > 0) {
            result = format.format((float)diff / Time.ONE_MIN_NS) + " min";
        } else if ((diff / Time.ONE_SEC_NS) > 0) {
            result = format.format((float)diff / Time.ONE_SEC_NS) + " sec";
        } else if ((diff / Time.ONE_MILLI_NS) > 0) {
            result = format.format((float)diff / Time.ONE_MILLI_NS) + " ms";
        } else if ((diff / Time.ONE_MICRO_NS) > 0) {
            result = format.format((float)diff / Time.ONE_MICRO_NS) + " us";
        } else {
            result = diff + " ns";
        }

        return result;
    }

    /**
     * Divides the elapsed time by the provided divisor and chooses the best format for time
     * difference. Rounds to two decimal places e.g. 1.43 ms
     *
     * @param divisor divisor must be greater than 0
     * @return elapsed time divided by divisor rounded to two decimal places
     */
    public String chooseBestDivide(int divisor) {
        if (divisor <= 0) {
            throw new IllegalArgumentException("Divisor can not be smaller than 1!");
        }

        return chooseBest(getNanos() / divisor);
    }
}
