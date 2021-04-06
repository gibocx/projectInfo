package download.actions;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DownloadActionFactoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullArgument() {
        DownloadAction action = DownloadActionFactory.newAction(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void actionNull() {
        Map<String, String> data = new HashMap<>(1);
        data.put("action",null);
        DownloadAction action = DownloadActionFactory.newAction(data);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidClassName() {
        Map<String, String> data = new HashMap<>(1);
        data.put("action","null");
        DownloadAction action = DownloadActionFactory.newAction(data);
    }

}