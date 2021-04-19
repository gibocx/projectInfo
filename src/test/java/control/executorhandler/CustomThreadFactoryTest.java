package control.executorhandler;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ThreadFactory;

public class CustomThreadFactoryTest {

    @Test
    public void checkNaming() {
        ThreadFactory threadFactory = new CustomThreadFactory("valid");

        for(int i = 1; i <= 5; i++) {
            Assert.assertEquals("valid["+i+"]",
                    threadFactory.newThread(() -> {}).getName());
        }
    }
}