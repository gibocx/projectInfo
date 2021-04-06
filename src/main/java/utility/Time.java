package utility;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Time {
    public static final long SECONDS_PER_DAY = Duration.ofDays(1).getSeconds();

    public static final long ONE_MICRO_NS = 1000;
    public static final long ONE_MILLI_NS = ONE_MICRO_NS * 1000;
    public static final long ONE_SEC_NS = ONE_MILLI_NS * 1000;
    public static final long ONE_MIN_NS = ONE_SEC_NS * 60;
    public static final long ONE_HOUR_NS = ONE_MIN_NS * 60;
    public static final long ONE_DAY_NS = ONE_HOUR_NS * 24;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            .withLocale(Locale.GERMANY)
            .withZone(ZoneId.systemDefault());


    public static long getUnixTimestamp() {
        return Instant.now().getEpochSecond();
    }

    public static String getDate() {
        return formatter.format(Instant.now());
    }

    public static LocalDateTime curDateTime() {
        return LocalDateTime.now();
    }
}
