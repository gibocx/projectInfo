package download.methods;

import java.util.Map;

public class DownloadMethodFactory {
    public static DownloadMethod newMethod(Map<String, String> data) {
        if(data == null) {
            throw new IllegalArgumentException("data can not be null!");
        } else {
            if(!data.containsKey("method") || data.get("method") == null) {
                throw new IllegalArgumentException("data does not contain the key \"method\" or is null!");
            }
        }

        try {
            Class<?> clazz = Class.forName("download.methods.Download" + data.get("method"));
            return (DownloadMethod) clazz.getConstructor(Map.class).newInstance(data);
        } catch (ClassNotFoundException ex) {
            throw new IllegalArgumentException(data.get("method") + " is not valid DownloadMethod!");
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage() + " occurred in DownloadMethodFactory");
        }
    }
}
