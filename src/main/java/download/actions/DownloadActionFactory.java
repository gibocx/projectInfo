package download.actions;

import java.util.Map;

public class DownloadActionFactory {
    public static DownloadAction newAction(Map<String, String> data) {
        if(data == null) {
            throw new IllegalArgumentException("data can not be null!");
        } else {
            if(!data.containsKey("action") || data.get("action") == null) {
                throw new IllegalArgumentException("data does not contain the key \"action\" or is null!");
            }
        }

        try {
            Class<?> clazz = Class.forName("download.actions.Action" + data.get("action"));
            return (DownloadAction) clazz.getConstructor(Map.class).newInstance(data);
        } catch (ClassNotFoundException ex) {
            throw new IllegalArgumentException(data.get("action") + " is not valid DownloadAction!");
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage() + " occurred in DownloadActionFactory");
        }
    }
}
