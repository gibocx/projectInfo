package control.wrappers;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import download.preactions.PreAction;
import download.preactions.PreActionFactory;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class PreActionWrapper {
    private final Map<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private String name = "";
    private int[] rows = new int[0];

    @JsonAnySetter
    public void setMap(String name, String value) {
        this.map.put(name, value);
    }

    /**
     * @return array of rows or otherwise an empty array
     */
    public int[] getRows() {
        return rows;
    }

    public void setRows(int[] rows) {
        this.rows = rows;
    }

    public Optional<String> get(String key) {
        return Optional.of(map.get(key));
    }

    /**
     * @return specified name or empty String
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PreAction getPreAction() {
        return PreActionFactory.newAction(this);
    }
}
