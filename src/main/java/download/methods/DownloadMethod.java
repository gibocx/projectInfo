package download.methods;

import download.Category;
import download.actions.DownloadActions;

import java.util.Set;

public interface DownloadMethod {

    /**
     * @return true when the download was successfully
     */
    boolean download(Set<Category> categories, DownloadActions action);

    /**
     * Checks if the given Job shall be downloaded again
     *
     * @return true if it should be downloaded again
     */
    boolean check(Set<Category> categories);
}
