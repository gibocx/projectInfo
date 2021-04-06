package control.wrappers;

import download.actions.DownloadAction;
import download.actions.DownloadActionFactory;

import java.util.Map;

public class ActionWrapper {
    private Map<String, String> action;

    /**
     * Creates a new DownloadAction
     * @return new DownloadAction
     * @throws IllegalArgumentException when a problem occurs
     */
    public DownloadAction getAction() {
        return DownloadActionFactory.newAction(action);
    }

    public void setAction(Map<String, String> action) {
        this.action = action;
    }
}
