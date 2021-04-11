package utility;

import java.util.Map;

public class Contains {

    /**
     * Checks if the given Map contains all keys
     *
     * @param map
     * @param keys array of the expected key objects
     * @return true when all keys are in map
     */
    public static boolean containsAll(Map map, Object[] keys) {
        if (map == null || map.isEmpty()) {
            return false;
        }

        if (keys == null || keys.length == 0) {
            return false;
        }

        for (Object key : keys) {
            if (!map.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a String contains the given Substrings. This method is not case-sensitive
     *
     * @param str        string to check
     * @param substrings array of Substrings
     * @return true when the String contains all the Substrings
     */
    public static boolean containsSubStrings(String str, String[] substrings) {
        if (str == null || str.isEmpty() || str.trim().isEmpty()) {
            return false;
        }

        if (substrings == null || substrings.length == 0) {
            return false;
        }

        str = str.toUpperCase();

        for (String substring : substrings) {
            if (!str.contains(substring.toUpperCase())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if a String contains the given Substrings. This method is case-sensitive
     *
     * @param str        string to check
     * @param substrings array of Substrings
     * @return true when the String contains all the Substrings
     */
    public static boolean containsSubStringsCaseSensitive(String str, String[] substrings) {
        if (str == null || str.isEmpty() || str.trim().isEmpty()) {
            return false;
        }

        if (substrings == null || substrings.length == 0) {
            return false;
        }

        for (String substring : substrings) {
            if (!str.contains(substring)) {
                return false;
            }
        }

        return true;
    }
}
