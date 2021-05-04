package utility;

import download.Category;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Placeholders {
    /**
     * Replaces the placeholders in the given String. A placeholder is enclosed in #.
     * These placeholders do exist:
     * time : timeFormat e.g. <code>dd-M-yyyy_hh-mm-ss</code>
     * thread : id, name, threadGroup
     * random -> random integer
     * <p>
     * The placeholders ignores upper or lowercase letters. Therefore time equals tImE.
     * <p>
     * Some examples
     * #thread=id# -> current Thread id
     * #time=dd-M-yyyy_hh-mm-ss# -> current time formatted into the given format
     * #random# -> random integer
     *
     * @param str string including placeholders
     * @param cat Category name to replace #category#
     * @return String with replaced placeholders
     */
    public static String replace(final String str, final Category cat) {
        return replace(str.replace("#category#", cat.getName()));
    }

    /**
     * Replaces the placeholders in the given String. A placeholder is enclosed in #.
     * These placeholders do exist:
     * time : timeFormat e.g. <code>dd-M-yyyy_hh-mm-ss</code>
     * thread : id, name, threadGroup
     * random -> random integer
     * <p>
     * The placeholders ignores upper or lowercase letters. Therefore time equals tImE.
     * <p>
     * Some examples
     * #thread=id# -> current Thread id
     * #time=dd-M-yyyy_hh-mm-ss# -> current time formatted into the given format
     * #random# -> random integer
     *
     * @param str String including placeholders
     * @return String with replaced placeholders
     */
    public static String replace(String str) {

        if (str != null && !isValid(str)) {
            Matcher m = Pattern.compile("(?<=#)(.*?)(?=#)").matcher(str);

            while (m.find()) {
                switch (m.group().split("=")[0].toUpperCase(Locale.ENGLISH)) {
                    case "TIME":
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(m.group().split("=")[1]);
                        str = str.replace("#" + m.group() + "#", Time.curDateTime().format(formatter));
                        break;
                    case "THREAD":
                        str = str.replace("#" + m.group() + "#", threadInfo(m.group().split("=")[1]));
                        break;
                    case "RANDOM":
                        str = str.replace("#" + m.group() + "#", String.valueOf(ThreadRandom.rand()));
                        break;
                    default:
                }
            }
        }
        return str;
    }

    /**
     * check if the String contains valid Placeholders -> even amount of #
     * @param str
     * @return true when valid
     */
    private static boolean isValid(String str) {
        return ((str.length() - str.replaceAll("#", "").length()) % 2) != 0;
    }

    public static String threadInfo(String info) {
        if (info != null) {
            switch (info.toUpperCase(Locale.ENGLISH)) {
                case "ID":
                    return String.valueOf(Thread.currentThread().getId());

                case "NAME":
                    return Thread.currentThread().getName();

                case "THREADGROUP":
                    return Thread.currentThread().getThreadGroup().getName();

                default:
            }
        }
        return "";
    }
}
