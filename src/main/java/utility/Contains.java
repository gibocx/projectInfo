package utility;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.IntStream;

public class Contains {

    /**
     * Checks if the given Map contains all keys
     *
     * @param map  map which shall contains all keys
     * @param keys array of the expected key objects
     * @return true when all keys are in map
     */
    public static boolean containsAll(final Map map, final Object[] keys) {
        if (map == null || keys == null || map.isEmpty() || keys.length == 0) {
            return false;
        }

        return map.keySet().containsAll(Arrays.asList(keys));
    }

    /**
     * Checks of the Object[] array contains the given objects
     *
     * @param o
     * @param obj
     * @return true when the array contains the object
     */
    public static boolean contains(final Object o, final Object[] obj) {
        if(o == null || obj == null || obj.length == 0) {
            return false;
        }

        return Arrays.asList(obj).contains(o);
    }

    /**
     * Checks if a String contains the given Substrings. This method is not case-sensitive
     *
     * @param str        string to check
     * @param substrings array of Substrings
     * @return true when the String contains all the Substrings
     */
    public static boolean containsSubStrings(final String str, final String[] substrings) {
        if (str == null || substrings == null|| str.isEmpty()  || substrings.length == 0) {
            return false;
        }

        String finalStr = str.toUpperCase(Locale.ENGLISH);
        return Arrays.stream(substrings).allMatch(sub -> finalStr.contains(sub.toUpperCase(Locale.ENGLISH)));
    }

    /**
     * Checks if a String contains all the given Substrings. This method is case-sensitive
     *
     * @param str        String to check
     * @param substrings array of Substrings
     * @return true when the String contains all the Substrings
     */
    public static boolean containsSubStringsCaseSensitive(final String str, final String[] substrings) {
        if (str == null || substrings == null  || str.isEmpty() || substrings.length == 0) {
            return false;
        }

        return Arrays.stream(substrings).allMatch(str::contains);
    }

    public static boolean contains(final int value, final int[] values) {
        if (values.length == 0) {
            return false;
        }

        return IntStream.of(values).anyMatch(x -> x == value);
    }
}
