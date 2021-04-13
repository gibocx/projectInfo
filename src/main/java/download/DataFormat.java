package download;

public enum DataFormat {
    JSON("JSON"), CSV("CSV"), PLAINTEXT("PLAINTEXT"), NONE("NONE"), ;

    private final String value;

    DataFormat(String value) {
        this.value = value;
    }

    public static DataFormat enumOf(String value) {
        for (DataFormat format : values()) {
            if (format.value.equalsIgnoreCase(value)) {
                return format;
            }
        }
        return DataFormat.NONE;
    }
}
