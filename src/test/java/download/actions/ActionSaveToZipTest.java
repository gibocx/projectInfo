package download.actions;

import download.Category;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

public class ActionSaveToZipTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test(expected = IllegalArgumentException.class)
    public void constructorZipEntryNull() {
        new ActionSaveToZip("Pathss",null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorZipEntryEmpty() {
        ActionSaveToZip act = new ActionSaveToZip("Pathss","  ");
    }

    @Test
    public void zipEntryEndsNotInZip() {
        ActionSaveToZip act = new ActionSaveToZip("Pathss","file");
    }

    @Test
    public void getZipEntryName() {
        ActionSaveToZip action = new ActionSaveToZip("file.zip","entry#category#");

        Assert.assertEquals("entryCATEGORY",action.getZipEntryName(new Category("CATEGORY").getName()));
    }

    @Test
    public void action() {
        ActionSaveToZip action = new ActionSaveToZip(folder.getRoot().getPath()+"file.zip","entry");

        Assert.assertTrue(action.action(new byte[0],new Category("Test")));
        Assert.assertTrue((new File(folder.getRoot().getPath() + "file.zip")).exists());

        // File already exists
        Assert.assertFalse(action.action(new byte[0],new Category("Test")));
    }
}