package utility;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ContainsTest {
    private Map<String, String> map = new HashMap<>();
    private String[] keys = new String[]{"key1","key2","key3"};
    private String[] substrings = new String[]{"sub","STRING"};
    private String contains = "contains sub string";
    private String containsCaseSensitive = "contains sub STRING";

    @Test
    public void containsAllNullOrEmpty() {
        for(String key : keys) {
            map.put(key,"value");
        }

        // null
        Assert.assertFalse(Contains.containsAll(null,keys));
        Assert.assertFalse(Contains.containsAll(map,null));
        Assert.assertFalse(Contains.containsAll(null,null));

        //empty
        Assert.assertFalse(Contains.containsAll(map,new Object[0]));
        Assert.assertFalse(Contains.containsAll(new HashMap(),keys));
    }

    @Test
    public void containsAll() {
        for(String key : keys) {
            map.put(key,"value");
        }

        Assert.assertTrue(Contains.containsAll(map,keys));

        map.put("randomKey","key");
        Assert.assertTrue(Contains.containsAll(map,keys));

        map.remove(keys[0]);
        Assert.assertFalse(Contains.containsAll(map,keys));
    }

    @Test
    public void containsSubstringNullOrEmpty() {
        Assert.assertFalse(Contains.containsSubStrings(null,substrings));
        Assert.assertFalse(Contains.containsSubStrings(contains,null));
        Assert.assertFalse(Contains.containsSubStrings(null,null));

        Assert.assertFalse(Contains.containsSubStrings(contains,new String[0]));
        Assert.assertFalse(Contains.containsSubStrings(new String(),substrings));
        Assert.assertFalse(Contains.containsSubStrings(new String("   "),substrings));
    }

    @Test
    public void containsSubstring() {
        Assert.assertTrue(Contains.containsSubStrings(containsCaseSensitive,substrings));
        Assert.assertTrue(Contains.containsSubStrings(contains,substrings));

        Assert.assertFalse(Contains.containsSubStrings(contains,new String[]{"does not contains"}));
    }

    @Test
    public void containsSubstringCaseSensitiveNullOrEmpty() {
        Assert.assertFalse(Contains.containsSubStringsCaseSensitive(null,substrings));
        Assert.assertFalse(Contains.containsSubStringsCaseSensitive(contains,null));
        Assert.assertFalse(Contains.containsSubStringsCaseSensitive(null,null));

        Assert.assertFalse(Contains.containsSubStringsCaseSensitive(contains,new String[0]));
        Assert.assertFalse(Contains.containsSubStringsCaseSensitive(new String(),substrings));
        Assert.assertFalse(Contains.containsSubStringsCaseSensitive(new String("   "),substrings));
    }

    @Test
    public void containsSubstringCaseSensitive() {
        Assert.assertFalse(Contains.containsSubStringsCaseSensitive(contains,substrings));
        Assert.assertTrue(Contains.containsSubStringsCaseSensitive(containsCaseSensitive,substrings));
    }
}