package download.methods;

import download.Category;
import download.Download;
import download.DownloadActions;
import utility.CalcChecksum;
import utility.Placeholders;
import utility.Time;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

class DownloadOnchangeDelay implements DownloadMethod {
    private static final Logger logger = Logger.getLogger(DownloadOnchangeDelay.class.getName());
    private final String url, contentType;
    private final long minTimeDifference;

    public DownloadOnchangeDelay(Map<String, String> data) {
        url = data.get("url");
        contentType = data.get("contentType");
        try {
            minTimeDifference = Long.parseLong(data.get("timeDifference"));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("timeDifference can not be parsed!" + ex.getMessage());
        }

        checkAttributes();
    }

    private void checkAttributes() {
        if (url == null) {
            throw new IllegalArgumentException("url can not be null!");
        }

        if (minTimeDifference <= 0) {
            throw new IllegalArgumentException("timeDifference must be greater than 0!");
        }

        if (minTimeDifference < 5 * 60) {
            logger.info("Url: " + url + " is downloaded every " + minTimeDifference + " seconds!");
        }
    }

    public boolean download(Set<Category> categories, DownloadActions action) {
        action.init();

        for (Category category : categories) {
            if (checkCategory(category)) {
                byte[] data = Download.downloadByteArray(Placeholders.replace(url, category), contentType);

                category.setLastDownloaded(Time.getUnixTimestamp());

                if (CalcChecksum.checksum(data) != category.getChecksum()) {
                    category.setChecksum(CalcChecksum.checksum(data));
                    action.action(data, category);
                }
            }
        }

        action.finish();

        return true;
    }

    /**
     * Checks if the given Category shall be downloaded again
     *
     * @return true only if it should be downloaded again
     */
    private boolean checkCategory(Category cat) {
        return (cat.getLastDownloaded() + minTimeDifference < Time.getUnixTimestamp());
    }

    private long getOldestDownload(Set<Category> categories) {
        return categories.stream().min(Comparator.comparingLong(Category::getLastDownloaded))
                .orElse(Category.EMPTY_CATEGORY).getLastDownloaded();
    }

    /**
     * Checks if the given Job shall be downloaded again
     *
     * @return true if it should be downloaded again
     */
    public boolean check(Set<Category> categories) {
        return (getOldestDownload(categories) + minTimeDifference < Time.getUnixTimestamp());
    }

}
