package download.actions;

import control.wrappers.ActionWrapper;
import db.Connect;
import db.RunQuery;
import download.Category;
import download.actions.insert.InsertDataType;
import download.actions.insert.InsertInfo;
import download.actions.insert.InsertMethodFactory;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Logger;

class ActionDbInsert implements DownloadAction {
    private static final Logger logger = Logger.getLogger(ActionDbInsert.class.getName());
    private final InsertInfo in;
    private InsertDataType insertData;
    private boolean dataBaseAvailable;

    public ActionDbInsert(ActionWrapper action) {
        in = new InsertInfo(action.getMap());
    }

    public boolean action(final byte[] data, final Category category) {
        if (dataBaseAvailable) {
            return insertData.insert(new String(data, StandardCharsets.UTF_8), category);
        }

        return false;
    }

    @Override
    public boolean init() {
        dataBaseAvailable = Connect.isAvailable();

        if (dataBaseAvailable) {
            insertData = InsertMethodFactory.newMethod(in, new RunQuery(in.getQuery(), in.getSchema()));
        } else {
            logger.info("Database is currently not available. Skipped");
        }
        return dataBaseAvailable;
    }

    @Override
    public boolean finish() {
        if (dataBaseAvailable) {
            return insertData.finish();
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionDbInsert that = (ActionDbInsert) o;
        return Objects.equals(in, that.in) && Objects.equals(insertData, that.insertData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(in, insertData);
    }
}
