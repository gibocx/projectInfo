package download.preactions;

import control.wrappers.PreActionWrapper;

public class PreActionFactory {
    public static PreAction newAction(PreActionWrapper pre) {
        if (pre == null) {
            throw new IllegalArgumentException("PreActionWrapper can not be null!");
        } else {
            if (pre.getName() == null || pre.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("PreActionName can not be null or empty");
            }
        }

        try {
            Class<?> clazz = Class.forName("download.preactions.PreAction" + pre.getName());
            return (PreAction) clazz.getConstructor(PreActionWrapper.class).newInstance(pre);
        } catch (ClassNotFoundException ex) {
            throw new IllegalArgumentException(pre.getName() + " is not valid PreAction!");
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
