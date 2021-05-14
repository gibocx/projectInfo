package download.methods;

import download.Category;
import download.DownloadActions;

import java.util.Set;

class DownloadProgram implements DownloadMethod {
    public boolean download(Set<Category> categories, DownloadActions action) {

        return true;
    }

    public boolean check(Set<Category> categories) {
        return false;
    }
}
