package download;

import org.junit.Assert;
import org.junit.Test;

public class CategoryTest {
    private final Category category = new Category("name");

    @Test
    public void checksum() {
        Category c = category;
        Assert.assertEquals(0,c.getChecksum());

        c.setChecksum(1032);
        Assert.assertEquals(1032,c.getChecksum());

        c.setChecksum(-1032);
        Assert.assertEquals(-1032,c.getChecksum());
    }

    @Test
    public void lastDownloaded() {
        Category c = category;
        Assert.assertEquals(0,c.getLastDownloaded());

        c.setLastDownloaded(9392);
        Assert.assertEquals(9392,c.getLastDownloaded());
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeDownload() {
        category.setLastDownloaded(-199);
    }

    @Test
    public void getName() {
        Assert.assertEquals("name",category.getName());
    }
}