package download.actions;

import download.Category;
import org.junit.Assert;
import org.junit.Test;

public class ActionNothingTest {

    @Test
    public void constructorsDoNothing() {
        ActionNothing act = new ActionNothing();
        act = new ActionNothing(new Object());
    }

    @Test
    public void initTrue() {
        Assert.assertTrue((new ActionNothing()).init());
    }

    @Test
    public void actionTrue() {
        Assert.assertTrue((new ActionNothing()).action(new byte[0],new Category("name")));
    }

    @Test
    public void finishTrue() {
        Assert.assertTrue((new ActionNothing()).finish());
    }

}