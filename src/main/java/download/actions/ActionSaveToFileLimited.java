package download.actions;

import control.wrappers.ActionWrapper;
import download.Category;

class ActionSaveToFileLimited extends ActionSaveToFile implements DownloadAction {
    private final int maxFiles;

    public ActionSaveToFileLimited(String path, int maxFiles) {
        super(path);

        this.maxFiles = maxFiles;
    }

    public ActionSaveToFileLimited(ActionWrapper action) {
            this(action.getNullable("path"),Integer.valueOf(action.getNullable("maxFiles")));
    }

    public boolean action(byte[] data, Category category) {
        // check if files are less than maxFiles
        //if(maxFiles < current files)
        return super.action(data, category);
        //else
        //    return false;
    }
}
