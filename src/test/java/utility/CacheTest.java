package utility;

import org.junit.Assert;
import org.junit.Test;

public class CacheTest {

    @Test
    public void simpleTest() {
        Cache<String, String> cache = new Cache<>(300000L);

        for(int i = 0; i < 1000;i++) {
            cache.put(String.valueOf(i), String.valueOf(i));
        }

        Assert.assertEquals("10",cache.get(String.valueOf(10)).orElse(""));
        cache.clean();
        Assert.assertEquals("10",cache.get(String.valueOf(10)).orElse(""));
        cache.clear();
        Assert.assertEquals("",cache.get(String.valueOf(10)).orElse(""));
    }

    @Test
    public void timeoutTest() {
        Cache<String, String> cache = new Cache<>(1L);

        for(int i = 0; i < 1000;i++) {
            cache.put(String.valueOf(i), String.valueOf(i));
        }

        Assert.assertEquals("",cache.get(String.valueOf(10)).orElse(""));

    }

}