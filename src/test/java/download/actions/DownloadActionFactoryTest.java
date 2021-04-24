package download.actions;

import org.junit.Test;

public class DownloadActionFactoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullArgument() {
        DownloadAction action = DownloadActionFactory.newAction(null);
    }

}