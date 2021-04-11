package utility;

import org.junit.Assert;
import org.junit.Test;

public class ConcatTest {
    private String[] strs = new String[]{"this","is","a","string"};
    private String strConcat = "thisisastring";
    private String strConcatBeforeAfter = "BthisABisABaABstringA";

    private int[] ints = {1,2,3,4,5};
    private String intConcat = "12345";
    private String intConcatBeforeAfter = "B1AB2AB3AB4AB5A";

    @Test
    public void concatStringArr() {
        Assert.assertEquals(strConcat,Concat.concat(strs));
    }

    @Test
    public void concatStringArrNullOrEmpty() {
        Assert.assertEquals(null,Concat.concat(null));
        Assert.assertEquals(null,Concat.concat(null));
    }

    @Test
    public void concatObjectBeforeAfterNull() {
        Assert.assertEquals(null,Concat.concat((Object[])null,null,null));
        Assert.assertEquals(null,Concat.concat(new Object[0],null,null));

    }

    @Test
    public void concatObjectBeforeAfter() {
        Assert.assertEquals(strConcat,Concat.concat(strs,null,null));
        Assert.assertEquals(strConcatBeforeAfter,Concat.concat(strs,"B","A"));
    }

    @Test
    public void concatObjectBeforeAfterPrimitivesNull() {
        Assert.assertEquals(null,Concat.concat((int[])null,null,null));
        Assert.assertEquals(null,Concat.concat(new int[0],null,null));

    }

    @Test
    public void concatObjectBeforeAfterPrimitives() {
        Assert.assertEquals(intConcat,Concat.concat(ints,null,null));
        Assert.assertEquals(intConcatBeforeAfter,Concat.concat(ints,"B","A"));
    }
}