package download.actions;

import control.wrappers.ActionWrapper;
import download.Category;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

public class ActionSaveToFileTest {
    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullString() {
        DownloadAction action = new ActionSaveToFile((String) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorInvalidString() {
        DownloadAction action = new ActionSaveToFile("  ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWitheSpace() {
        DownloadAction action = new ActionSaveToFile("  ");
    }

    @Test
    public void initFinishTrue() {
        DownloadAction action = new ActionSaveToFile(folder.getRoot().getPath() + "test1");

        Assert.assertTrue(action.init());
        Assert.assertTrue(action.finish());
    }

    @Test
    public void action() {
        byte[] data = "Some good data this is".getBytes();

        DownloadAction action = new ActionSaveToFile(folder.getRoot().getPath() + "test1");

        Assert.assertTrue(action.action(data, new Category("category")));

        // File now exists -> return false
        Assert.assertFalse(action.action(data, new Category("category")));
    }

    @Test
    public void actionNull() {
        DownloadAction action = new ActionSaveToFile(folder.getRoot().getPath() + "test1");

        Assert.assertFalse(action.action(null, new Category("category")));
        Assert.assertFalse(action.action(new byte[0], null));
        Assert.assertFalse(action.action(null, null));
    }

    @Test
    public void checkPlaceholderReplacement() {
        DownloadAction action = new ActionSaveToFile(folder.getRoot().getPath() + "#category#");

        Assert.assertTrue(action.action(new byte[0], new Category("category")));
        Assert.assertTrue((new File(folder.getRoot().getPath() + "category")).exists());
    }
}