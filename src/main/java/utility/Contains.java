package utility;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.IntStream;

public class Contains {

    /**
     * Checks if the given Map contains all keys
     *
     * @param map   map which shall contains all keys
     * @param keys  array of the expected key objects
     * @return      true when all keys are in map
     */
    public static boolean containsAll(Map map, Object[] keys) {
        if (map == null || map.isEmpty()) {
            return false;
        }

        if (keys == null || keys.length == 0) {
            return false;
        }

        return map.keySet().containsAll(Arrays.asList(keys));
    }

    /**
     * Checks of the Object[] array contains the given objects
     * @param o
     * @param obj
     * @return true when the array contains the object
     */
    public static boolean contains(Object o, Object[] obj) {
        return Arrays.stream(obj).anyMatch(o::equals);
    }

    /**
     * Checks if a String contains the given Substrings. This method is not case-sensitive
     *
     * @param str        string to check
     * @param substrings array of Substrings
     * @return true when the String contains all the Substrings
     */
    public static boolean containsSubStrings(String str, String[] substrings) {
        if (str == null || str.isEmpty() || str.isEmpty()) {
            return false;
        }

        if (substrings == null || substrings.length == 0) {
            return false;
        }

        final String finalStr = str.toUpperCase(Locale.ENGLISH);
        return !Arrays.stream(substrings).anyMatch(sub -> !(finalStr.contains(sub.toUpperCase(Locale.ENGLISH))));
    }

    /**
     * Checks if a String contains all the given Substrings. This method is case-sensitive
     *
     * @param str        String to check
     * @param substrings array of Substrings
     * @return           true when the String contains all the Substrings
     */
    public static boolean containsSubStringsCaseSensitive(String str, String[] substrings) {
        if (str == null || str.isEmpty() || str.isEmpty()) {
            return false;
        }

        if (substrings == null || substrings.length == 0) {
            return false;
        }

        return !Arrays.stream(substrings).anyMatch(sub -> !(str.contains(sub)));
    }

    public static boolean contains(int value, int[] values) {
        if(values.length == 0) {
            return false;
        }

        return IntStream.of(values).anyMatch(x -> x == value);
    }
}
