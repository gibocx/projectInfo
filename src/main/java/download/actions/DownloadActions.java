package download.actions;

import download.Category;

import java.util.Set;

public class DownloadActions implements DownloadAction {
    private final Set<DownloadAction> actions;

    public DownloadActions(Set<DownloadAction> actions) {
        this.actions = actions;
    }

    @Override
    public boolean action(byte[] data, Category category) {
        boolean success = true;

        for(DownloadAction action : actions) {
            if(action.action(data, category)) {
                success = false;
            }
        }

        return success;
    }

    @Override
    public boolean init() {
        boolean success = true;

        for(DownloadAction action : actions) {
            if(!action.init()) {
                success = false;
            }
        }

        return success;
    }

    @Override
    public boolean finish() {
        boolean success = true;

        for(DownloadAction action : actions) {
            if(!action.finish()) {
                success = false;
            }
        }

        return success;
    }
}
