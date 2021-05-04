package utility;

public class Concat {
    public static String concat(String[] strs) {
        if (strs == null || strs.length == 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder();

        for (String str : strs) {
            builder.append(str);
        }

        return builder.toString();
    }

    public static String concat(Object[] values, String before, String after) {
        if (values == null || values.length == 0) {
            return null;
        }

        if (before == null) {
            before = "";
        }

        if (after == null) {
            after = "";
        }

        StringBuilder builder = new StringBuilder();

        for (Object value : values) {
            builder.append(before).append(value).append(after);
        }

        return builder.toString();
    }

    public static String concat(int[] values, String before, String after) {
        if (values == null || values.length == 0) {
            return null;
        }

        if (before == null) {
            before = "";
        }

        if (after == null) {
            after = "";
        }

        StringBuilder builder = new StringBuilder();

        for (Object value : values) {
            builder.append(before).append(value).append(after);
        }

        return builder.toString();
    }
}
