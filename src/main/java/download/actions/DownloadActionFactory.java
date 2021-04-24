package download.actions;

import control.wrappers.ActionWrapper;


public class DownloadActionFactory {
    public static DownloadAction newAction(ActionWrapper action) {
        if (action == null) {
            throw new IllegalArgumentException("ActionWrapper can not be null!");
        } else {
            if (action.getName() == null || action.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("ActionName can not be null or empty!");
            }
        }

        try {
            Class<?> clazz = Class.forName("download.actions.Action" + action.getName());
            return (DownloadAction) clazz.getConstructor(ActionWrapper.class).newInstance(action);
        } catch (ClassNotFoundException ex) {
            throw new IllegalArgumentException(action.getName() + " is not valid DownloadAction!");
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
