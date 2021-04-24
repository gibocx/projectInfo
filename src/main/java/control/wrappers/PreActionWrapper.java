package control.wrappers;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class PreActionWrapper {
    private String name = "";
    private int[] rows = new int[0];
    private final Map<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public void setRows(int[] rows) {
        this.rows = rows;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonAnySetter
    public void setMap(String name, String value) {
        this.map.put(name,value);
    }

    /**
     * @return array of rows or otherwise an empty array
     */
    public int[] getRows() {
        return rows;
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
}
