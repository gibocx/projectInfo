package download.methods;

import download.Category;
import download.Download;
import download.actions.DownloadActions;
import utility.CalcChecksum;
import utility.Placeholders;
import utility.Time;

import java.util.Map;
import java.util.Set;

public class DownloadOnchange implements DownloadMethod {
    private final String url, contentType;

    public DownloadOnchange(Map<String, String> data) {
        url = data.get("url");
        contentType = data.get("contentType");

        checkAttributes();
    }

    private void checkAttributes() {
        if (url == null) {
            throw new IllegalArgumentException("url can not be null!");
        }
    }

    public boolean download(Set<Category> categories, DownloadActions action) {
        action.init();

        for (Category category : categories) {
            byte[] data = Download.downloadByteArray(Placeholders.replace(url,category.getName()), contentType);

            category.setLastDownloaded(Time.getUnixTimestamp());

            if (CalcChecksum.checksum(data) != category.getChecksum()) {
                category.setChecksum(CalcChecksum.checksum(data));
                action.action(data, category);
            }
        }

        action.finish();

        return true;
    }

    /**
     * Checks if the given category array and determines if it shall be downloaded again.
     * Always true because it is not possible to know if a change happened without a download
     *
     * @param categories Categories to check
     * @return always true
     */
    public boolean check(Set<Category> categories) {
        return true;
    }
}
