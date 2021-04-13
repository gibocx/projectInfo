package download.actions;

import db.RunQuery;
import db.Connect;
import download.Category;
import insert.InsertDataType;
import insert.InsertInfo;
import insert.InsertMethodFactory;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.Map;
import java.util.logging.Logger;

class ActionDbInsert implements DownloadAction {
    private static final Logger logger = Logger.getGlobal();
    private final InsertInfo in;
    private InsertDataType insertData;
    private Connection con;

    public ActionDbInsert(Map<String, String> data) {
        in = new InsertInfo(data);
    }

    public boolean action(byte[] data, Category category) {
        String str = new String(data, StandardCharsets.UTF_8);

        insertData.insert(str, category);

        return true;
    }

    @Override
    public boolean init() {
        con = Connect.getConnection(in.getSchema());
        insertData = InsertMethodFactory.newMethod(in, new RunQuery(in.getQuery(), con));

        return (con != null && insertData != null);
    }

    @Override
    public boolean finish() {

        Connect.closeQuietly(con);
        return true;
    }
}
