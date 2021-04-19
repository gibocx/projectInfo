package download.actions.insert;

import db.RunQuery;

public class InsertMethodFactory {
    public static InsertDataType newMethod(InsertInfo in, RunQuery run) {
        if (in == null) {
            throw new IllegalArgumentException("in can not be null!");
        }

        if (run == null) {
            throw new IllegalArgumentException("run can not be null!");
        }

        try {
            Class<?> clazz = Class.forName("download.actions.insert.Insert" + in.getDataType());
            return (InsertDataType) clazz.getConstructor(InsertInfo.class, RunQuery.class).newInstance(in, run);
        } catch (ClassNotFoundException ex) {
            throw new IllegalArgumentException(in.getDataType() + " is not valid InsertDataType!");
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage() + " occurred in DownloadActionFactory");
        }
    }
}
