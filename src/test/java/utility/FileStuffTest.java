package utility;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;


public class FileStuffTest {
    private File valid, invalid, dir, tmp;

    @Before
    public void before() throws URISyntaxException {
        valid = new File(FileStuffTest.class.getResource("valid").toURI());
        invalid = new File(valid.getParent() + "invalid");
        dir = new File(valid.getParent() + "dir");
        tmp = new File(valid.getParent() + "tmp");
    }

    @Test
    public void isValidFile() {
        Assert.assertTrue(FileStuff.isValid(valid));

        Assert.assertFalse(FileStuff.isValid(invalid));
        Assert.assertFalse(FileStuff.isValid(dir));
        Assert.assertFalse(FileStuff.isValid((File)null));
    }

    @Test
    public void isValidPath() {
        Assert.assertTrue(FileStuff.isValid(valid.getPath()));

        Assert.assertFalse(FileStuff.isValid(invalid.getPath()));
        Assert.assertFalse(FileStuff.isValid(dir.getPath()));
        Assert.assertFalse(FileStuff.isValid((String)null));
    }

    @Test
    public void createFileFile() {
        Assert.assertFalse(FileStuff.createFile(valid));
        Assert.assertFalse(FileStuff.createFile((File)null));

        tmp.delete();
        Assert.assertTrue(FileStuff.createFile(tmp));
        if(!tmp.delete()) {
            Assert.fail("File can not be deleted!");
        }
    }

    @Test
    public void createFilePath() {
        Assert.assertFalse(FileStuff.createFile(valid.getPath()));
        Assert.assertFalse(FileStuff.createFile((String)null));

        tmp.delete();
        Assert.assertTrue(FileStuff.createFile(tmp.getPath()));
        if(!tmp.delete()) {
            Assert.fail("File can not be deleted!");
        }
    }

    @Test
    public void deleteFileFile() {
        Assert.assertFalse(FileStuff.delete(invalid));
        Assert.assertFalse(FileStuff.delete((File)null));

        FileStuff.createFile(tmp);
        Assert.assertTrue(FileStuff.delete(tmp));
        if(tmp.exists()) {
            Assert.fail("File still exists!");
        }
    }

    @Test
    public void deleteFilePath() {
        Assert.assertFalse(FileStuff.delete(invalid.getPath()));
        Assert.assertFalse(FileStuff.delete((String)null));

        FileStuff.createFile(tmp);
        Assert.assertTrue(FileStuff.delete(tmp.getPath()));
        if(tmp.exists()) {
            Assert.fail("File still exists!");
        }
    }
}