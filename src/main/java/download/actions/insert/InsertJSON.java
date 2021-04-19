package download.actions.insert;

import db.RunQuery;
import download.Category;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

class InsertJSON implements InsertDataType {
    private static final Logger logger = Logger.getGlobal();
    private final RunQuery run;
    private final String desiredObject;
    private final String[] desiredKeys;

    public InsertJSON(InsertInfo in, RunQuery run) {
        if (run == null) {
            throw new IllegalArgumentException("run can not be empty!");
        }

        if (in == null) {
            throw new IllegalArgumentException("in can not be empty!");
        }

        this.run = run;
        desiredObject = in.get("desiredObject");

        if(desiredObject != null || !desiredObject.isEmpty()) {
            logger.fine("Set desiredObject to " + desiredObject);
        }

        int i = 1;
        while (in.get("argument" + i) != null) {
            i++;
        }
        desiredKeys = new String[i - 1];

        String tmp;
        while ((tmp = in.get("argument" + i)) != null) {
            desiredKeys[i - 1] = tmp;
            i++;
        }
    }

    @Override
    public boolean insert(String str, Category category) {
        JSONArray arr = getJSONArray(str);
        JSONObject obj;

        for (int i = 0; i < arr.length(); i++) {
            obj = arr.getJSONObject(i);

            if (checkJSON(obj)) {
                run.add(computeValues(obj));
            }
        }

        return run.execute();
}

    @Override
    public boolean finish() {
        return run.close();
    }

    private boolean checkJSON(JSONObject obj) {
        for(String value : desiredKeys) {
            if(obj.isNull(value)) {
                return false;
            }
        }
        return true;
    }

    private String[] computeValues(JSONObject obj) {
        String[] values = new String[desiredKeys.length + 1];

        for(int i = 0; i < desiredKeys.length; i++) {
            values[i] = obj.getString(desiredKeys[i]);
        }

        return values;
    }


    private JSONArray getJSONArray(String string) throws JSONException {
        JSONArray arr;
        if (string.trim().charAt(0) == '{') {
            JSONObject jsonObject = new JSONObject(string);
            arr = jsonObject.getJSONArray(desiredObject);
        } else {
            arr = new JSONArray(string);
        }
        return arr;
    }
}
