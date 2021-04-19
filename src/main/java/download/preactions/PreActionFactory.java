package download.preactions;

import download.actions.DownloadAction;

import java.util.Map;

public class PreActionFactory {
    public static PreAction newAction(Map<String, String> data) {
        if (data == null) {
            throw new IllegalArgumentException("data can not be null!");
        } else {
            if (!data.containsKey("action") || data.get("action") == null) {
                throw new IllegalArgumentException("data does not contain the key \"action\" or is null!");
            }
        }

        try {
            Class<?> clazz = Class.forName("download.preactions.PreAction" + data.get("action"));
            return (PreAction) clazz.getConstructor(Map.class).newInstance(data);
        } catch (ClassNotFoundException ex) {
            throw new IllegalArgumentException(data.get("action") + " is not valid PreAction!");
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
