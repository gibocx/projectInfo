package utility;

import org.junit.Assert;
import org.junit.Test;

public class ConcatTest {
    private final String[] strs = new String[]{"this","is","a","string"};
    private final String strConcat = "thisisastring";
    private final String strConcatBeforeAfter = "BthisABisABaABstringA";

    private final int[] ints = {1,2,3,4,5};
    private final String intConcat = "12345";
    private final String intConcatBeforeAfter = "B1AB2AB3AB4AB5A";

    @Test
    public void concatStringArr() {
        Assert.assertEquals(strConcat,Concat.concat(strs));
    }

    @Test
    public void concatStringArrNullOrEmpty() {
        Assert.assertNull(Concat.concat(null));
        Assert.assertNull(Concat.concat(null));
    }

    @Test
    public void concatObjectBeforeAfterNull() {
        Assert.assertNull(Concat.concat((Object[]) null, null, null));
        Assert.assertNull(Concat.concat(new Object[0], null, null));

    }

    @Test
    public void concatObjectBeforeAfter() {
        Assert.assertEquals(strConcat,Concat.concat(strs,null,null));
        Assert.assertEquals(strConcatBeforeAfter,Concat.concat(strs,"B","A"));
    }

    @Test
    public void concatObjectBeforeAfterPrimitivesNull() {
        Assert.assertNull(Concat.concat((int[]) null, null, null));
        Assert.assertNull(Concat.concat(new int[0], null, null));

    }

    @Test
    public void concatObjectBeforeAfterPrimitives() {
        Assert.assertEquals(intConcat,Concat.concat(ints,null,null));
        Assert.assertEquals(intConcatBeforeAfter,Concat.concat(ints,"B","A"));
    }
}