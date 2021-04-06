package utility;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ThreadRandom.class, Time.class})
public class PlaceholdersTest {

    @Test
    public void threadInfo() {
        Thread t = Thread.currentThread();
        Assert.assertEquals(String.valueOf(t.getId()),Placeholders.threadInfo("id"));
        Assert.assertEquals(String.valueOf(t.getId()),Placeholders.threadInfo("iD"));

        Assert.assertEquals(t.getName(),Placeholders.threadInfo("name"));
        Assert.assertEquals(t.getName(),Placeholders.threadInfo("nAmE"));

        Assert.assertEquals(t.getThreadGroup().getName(),Placeholders.threadInfo("threadGroup"));
        Assert.assertEquals(t.getThreadGroup().getName(),Placeholders.threadInfo("tHrEaDgRoUp"));

        Assert.assertEquals("",Placeholders.threadInfo(null));
        Assert.assertEquals("",Placeholders.threadInfo("null"));
    }

    @Test
    public void replaceInvalidPlaceholder() {
        Assert.assertEquals("#category##",Placeholders.replace("#category##"));
        Assert.assertNull(Placeholders.replace(null));
    }

    @Test
    public void placeholders() {
        Assert.assertEquals("Id"+Thread.currentThread().getId(),Placeholders.replace("Id#thread=id#"));

        PowerMockito.mockStatic(ThreadRandom.class);
        when(ThreadRandom.rand()).thenReturn(1234);
        Assert.assertEquals("Random1234",Placeholders.replace("Random#random#"));
    }

    @Test
    public void placeholderTime() {
        final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(1989, 1, 13,12,34,56);
        PowerMockito.mockStatic(Time.class);

        when(Time.curDateTime()).thenReturn(LOCAL_DATE_TIME);
        Assert.assertEquals("Time : 13.01.1989  12:34:56",Placeholders.replace("Time : #time=dd.MM.yyyy  HH:mm:ss#"));
    }
}