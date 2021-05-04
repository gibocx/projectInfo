package control.wrappers;

import download.actions.DownloadAction;
import download.actions.DownloadActionFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

public class ActionWrapper {
    private final Map<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public static Set<DownloadAction> getActions(Set<ActionWrapper> set) {
        Set<DownloadAction> out = new HashSet<>();

        for (ActionWrapper action : set) {
            out.add(action.getAction());
        }

        return out;
    }

    /**
     * Creates a new DownloadAction
     *
     * @return new DownloadAction
     * @throws IllegalArgumentException when a problem occurs
     */
    public DownloadAction getAction() {
        return DownloadActionFactory.newAction(this);
    }

    public void setAction(Map<String, String> action) {
        this.map.putAll(action);
    }

    public String getName() {
        return map.get("name");
    }

    public Optional<String> get(String key) {
        return Optional.of(map.get(key));
    }

    public String getNullable(String key) {
        return map.get(key);
    }

    public Map<String, String> getMap() {
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionWrapper that = (ActionWrapper) o;
        return map.equals(that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}
