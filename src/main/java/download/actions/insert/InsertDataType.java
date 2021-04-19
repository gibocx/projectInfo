package download.actions.insert;

import download.Category;

public interface InsertDataType {
    boolean insert(String str, Category category);

    boolean finish();
}
