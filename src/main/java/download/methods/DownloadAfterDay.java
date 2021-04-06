package download.methods;

import utility.Time;

import java.util.Map;

public class DownloadAfterDay extends DownloadAfter {
    public DownloadAfterDay(Map<String, String> data) {
        super(data, Time.SECONDS_PER_DAY);
    }
}
