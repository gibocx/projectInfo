package download;

import control.executorhandler.ExecutorHandler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.net.URISyntaxException;
import java.util.concurrent.ScheduledFuture;

import static org.mockito.ArgumentMatchers.*;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest(ExecutorHandler.class)
public class UserAgentPoolTest {
    @Mock
    ScheduledFuture<?> t;

    private final File f = new File(UserAgentPoolTest.class.getResource("userAgents.txt").toURI());
    private final File invalid = new File(f.getParent() + "invalid");

    public UserAgentPoolTest() throws URISyntaxException {
    }

    @Test
    public void stdAgent() {
        UserAgentPool.clear();

        Assert.assertNotNull(UserAgentPool.getUserAgent());
        Assert.assertEquals(0,UserAgentPool.getPoolSize());
    }

    @Test
    public void setDuplicateUserAgents() {
        UserAgentPool.clear();
        UserAgentPool.addAgent("agent");

        Assert.assertEquals("agent",UserAgentPool.getUserAgent());
        Assert.assertEquals(1,UserAgentPool.getPoolSize());

        UserAgentPool.addAgent("agent");
        UserAgentPool.addAgent("agent");

        Assert.assertEquals(3,UserAgentPool.getPoolSize());

    }

    @Test
    public void AddAndClear() {
        UserAgentPool.clear();
        Assert.assertEquals(0,UserAgentPool.getPoolSize());

        UserAgentPool.addAgent("agent");
        Assert.assertEquals(1,UserAgentPool.getPoolSize());

        UserAgentPool.clear();
        Assert.assertEquals(0,UserAgentPool.getPoolSize());
    }

    @Test
    public void readFromFileEnum() {
        UserAgentPool.clear();

        UserAgentPool.setDataFormat(DataFormat.NONE);
        UserAgentPool.readAgentsFromFile(f);
        Assert.assertEquals(0,UserAgentPool.getPoolSize());

        UserAgentPool.setDataFormat(DataFormat.PLAINTEXT);
        UserAgentPool.readAgentsFromFile(f);
        Assert.assertEquals(5,UserAgentPool.getPoolSize());

        UserAgentPool.setDataFormat((DataFormat)null);
        UserAgentPool.readAgentsFromFile(f);
        Assert.assertEquals(0,UserAgentPool.getPoolSize());
    }

    @Test
    public void readFromFileString() {
        UserAgentPool.clear();
        UserAgentPool.setDataFormat("NONE");
        UserAgentPool.readAgentsFromFile(f);
        Assert.assertEquals(0,UserAgentPool.getPoolSize());

        UserAgentPool.setDataFormat("PLAINTEXT");
        UserAgentPool.readAgentsFromFile(f);
        Assert.assertEquals(5,UserAgentPool.getPoolSize());
        UserAgentPool.clear();

        UserAgentPool.setDataFormat("");
        UserAgentPool.readAgentsFromFile(f);
        Assert.assertEquals(0,UserAgentPool.getPoolSize());

        UserAgentPool.setDataFormat((String)null);
        UserAgentPool.readAgentsFromFile(f);
        Assert.assertEquals(0,UserAgentPool.getPoolSize());
    }

    @Test
    public void readFromFile() {
        UserAgentPool.clear();
        UserAgentPool.setDataFormat(DataFormat.PLAINTEXT);

        Assert.assertTrue(UserAgentPool.readAgentsFromFile(f));
        Assert.assertEquals(5,UserAgentPool.getPoolSize());

        UserAgentPool.setUserAgentFile(f);
        Assert.assertTrue(UserAgentPool.readAgentsFromFile());


        Assert.assertFalse(UserAgentPool.readAgentsFromFile(invalid));
        Assert.assertEquals(5,UserAgentPool.getPoolSize());

        Assert.assertFalse(UserAgentPool.readAgentsFromFile((File)null));
        Assert.assertEquals(5,UserAgentPool.getPoolSize());
    }

    @Test
    public void setUserAgentFile() {
        UserAgentPool.clear();
        UserAgentPool.setDataFormat(DataFormat.PLAINTEXT);

        Assert.assertTrue(UserAgentPool.setUserAgentFile(f));
        UserAgentPool.readAgentsFromFile();
        Assert.assertEquals(5,UserAgentPool.getPoolSize());
        UserAgentPool.clear();

        Assert.assertFalse(UserAgentPool.setUserAgentFile((File)null));
        Assert.assertFalse(UserAgentPool.setUserAgentFile(invalid));
    }

    @Test
    public void scheduleReadAgentsReload() {
        PowerMockito.mockStatic(ExecutorHandler.class);
        ScheduledFuture<?> future = Mockito.mock(ScheduledFuture.class);

        when(ExecutorHandler.scheduleAtFixedRate(any(Runnable.class), anyInt()))
                .thenReturn(Mockito.mock(ScheduledFuture.class));

        Assert.assertTrue(UserAgentPool.scheduleReadAgentsReload(0));
        Assert.assertTrue(UserAgentPool.scheduleReadAgentsReload(1));

        when(future.cancel(anyBoolean())).thenReturn(false);
        Assert.assertFalse(UserAgentPool.scheduleReadAgentsReload(1));
    }
}