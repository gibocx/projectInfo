package control.wrappers;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.Map;
import java.util.TreeMap;

public class DatabaseWrapper {
    private String user;
    private String password;
    private String jdbcUrl;
    private final Map<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public boolean isValid() {
        return (user != null && password != null && jdbcUrl != null);
    }

    public String getNullable(String key) {return map.get(key);}

    public Map<String, String> getMap() {
        return map;
    }

    @JsonAnySetter
    public void setMap(String name, String value) {
        this.map.put(name,value);
    }
}
