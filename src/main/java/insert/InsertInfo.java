package insert;

import java.util.Map;

public class InsertInfo {
    private final String dataType, schema, query;
    private final Map<String, String> data;

    public InsertInfo(Map<String, String> data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Map can not be empty or null!");
        }

        this.dataType = data.get("dataType");
        this.schema = data.get("schema");
        this.query = data.get("query");

        data.remove("dataType");
        data.remove("schema");
        data.remove("query");

        this.data = data;

        if (query == null) {
            throw new IllegalArgumentException("Query can not be null!");
        }

        if (dataType == null) {
            throw new IllegalArgumentException("dataType can not be null!");
        }

        if (schema == null) {
            throw new IllegalArgumentException("schema can not be null!");
        }
    }

    public String getDataType() {
        return dataType;
    }

    public String getSchema() {
        return schema;
    }

    public String getQuery() {
        return query;
    }

    public String get(String key) {
        return data.get(key);
    }
}
