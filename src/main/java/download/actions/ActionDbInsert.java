package download.actions;

import db.Connect;
import db.RunQuery;
import download.Category;
import download.actions.insert.InsertDataType;
import download.actions.insert.InsertInfo;
import download.actions.insert.InsertMethodFactory;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.Map;
import java.util.logging.Logger;

class ActionDbInsert implements DownloadAction {
    private static final Logger logger = Logger.getGlobal();
    private final InsertInfo in;
    private InsertDataType insertData;
    private boolean dataBaseAvailable;

    public ActionDbInsert(Map<String, String> data) {
        in = new InsertInfo(data);
    }

    public boolean action(byte[] data, Category category) {
        if(dataBaseAvailable) {
            String str = new String(data, StandardCharsets.UTF_8);
            insertData.insert(str, category);
        }

        return dataBaseAvailable;
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
            insertData.finish();
        }

        return dataBaseAvailable;
    }
}
