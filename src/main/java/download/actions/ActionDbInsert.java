package download.actions;

import control.wrappers.ActionWrapper;
import db.Connect;
import db.RunQuery;
import download.Category;
import download.actions.insert.InsertDataType;
import download.actions.insert.InsertInfo;
import download.actions.insert.InsertMethodFactory;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

class ActionDbInsert implements DownloadAction {
    private static final Logger logger = Logger.getGlobal();
    private final InsertInfo in;
    private InsertDataType insertData;
    private boolean dataBaseAvailable;

    public ActionDbInsert(ActionWrapper action) {
        in = new InsertInfo(action.getMap());
    }

    public boolean action(byte[] data, Category category) {
        if(dataBaseAvailable) {
            String str = new String(data, StandardCharsets.UTF_8);
            return insertData.insert(str, category);
        }

        return false;
    }

    @Override
    public boolean init() {
        dataBaseAvailable = Connect.isAvailable();

        if(dataBaseAvailable) {
            insertData = InsertMethodFactory.newMethod(in, new RunQuery(in.getQuery(), in.getSchema()));
        }
        return dataBaseAvailable;
    }

    @Override
    public boolean finish() {
        if(dataBaseAvailable) {
            return insertData.finish();
        }

        return false;
    }
}
