package utility;

import org.junit.Assert;
import org.junit.Test;

public class TimeDiffTest {
    private final TimeDiff time = new TimeDiff();

    @Test
    public void format() {
        String[] strs = {"ns","us", "ms"};
        long value = 123;

        for(int i = 1; i <= strs.length; i++) {
            Assert.assertEquals("123 "+strs[i-1],TimeDiff.chooseBest(value));
            value *= 1000;
        }

        final long ONE_SEC = 1000000000L;
        final long ONE_MINUTE = ONE_SEC * 60;

        Assert.assertEquals("1,62 sec", TimeDiff.chooseBest((long)(ONE_SEC * 1.62)));
        Assert.assertEquals("1,62 min", TimeDiff.chooseBest(ONE_SEC * 97));
        Assert.assertEquals("1,62 hours", TimeDiff.chooseBest(ONE_MINUTE * 97));
        Assert.assertEquals("1,62 days", TimeDiff.chooseBest(ONE_MINUTE * 60 * 39));
    }

    @Test(expected = IllegalArgumentException.class)
    public void chooseBestDivisorZero() {
        time.chooseBestDivide(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void chooseBestDivisorNegative() {
        time.chooseBestDivide(-1);
    }
}