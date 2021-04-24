package control.wrappers;

import download.actions.DownloadAction;
import download.actions.DownloadActionFactory;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class ActionWrapper {
    private final Map<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * Creates a new DownloadAction
     *
     * @return new DownloadAction
     * @throws IllegalArgumentException when a problem occurs
     */
    public DownloadAction getAction() {
        return DownloadActionFactory.newAction(this);
    }

    public String getName() {
        return map.get("name");
    }

    public void setAction(Map<String, String> action) {
        this.map.putAll(action);
    }

    public Optional<String> get(String key) {
        return Optional.of(map.get(key));
    }

    public String getNullable(String key) {return map.get(key);}

    public Map<String, String> getMap() {
        return map;
    }

}
