package control.wrappers;

import download.actions.DownloadAction;
import download.actions.DownloadActionFactory;

import java.util.Map;
import java.util.TreeMap;

public class ActionWrapper {
    private final Map<String, String> action = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * Creates a new DownloadAction
     *
     * @return new DownloadAction
     * @throws IllegalArgumentException when a problem occurs
     */
    public DownloadAction getAction() {
        return DownloadActionFactory.newAction(action);
    }

    public void setAction(Map<String, String> action) {
        this.action.putAll(action);
    }
}
