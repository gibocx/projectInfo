package download.methods;

import download.Category;
import download.DownloadActions;

import java.util.Set;

class DownloadNothing implements DownloadMethod {

    /**
     * Only runs the provided action
     *
     * @param categories
     * @param action
     * @return always true.
     */
    @Override
    public boolean download(Set<Category> categories, DownloadActions action) {
        action.init();

        for (Category cat : categories) {
            action.action(new byte[0], cat);
        }

        action.finish();
        return true;
    }

    /**
     * Checks if the given Job shall be downloaded again
     *
     * @param categories
     * @return always true
     */
    @Override
    public boolean check(Set<Category> categories) {
        return true;
    }
}
