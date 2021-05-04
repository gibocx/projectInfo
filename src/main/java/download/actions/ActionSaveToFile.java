package download.actions;

import control.wrappers.ActionWrapper;
import download.Category;
import utility.FileStuff;
import utility.Placeholders;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

class ActionSaveToFile implements DownloadAction {
    private static final Logger logger = Logger.getLogger(ActionSaveToFile.class.getName());
    private final String path;

    public ActionSaveToFile(String path) {
        if (path != null && !path.trim().isEmpty()) {
            this.path = path;
        } else {
            throw new IllegalArgumentException("filePath can not be empty!");
        }
    }

    public ActionSaveToFile(ActionWrapper action) {
        this(action.getNullable("path"));
    }

    public boolean action(byte[] data, Category category) {
        if (data == null || category == null) {
            return false;
        }

        String compPath = computePath(category);

        try {
            if (FileStuff.createFile(compPath)) {
                FileOutputStream outputStream = new FileOutputStream(compPath);
                outputStream.write(data);
                outputStream.close();
                return true;
            }
        } catch (IOException ex) {
            FileStuff.delete(compPath);
            logger.log(Level.INFO, "Unable to write data to file", ex);
        }

        return false;
    }

    String computePath(Category category) {
        return Placeholders.replace(this.path, category);
    }

    String computePath() {
        return Placeholders.replace(this.path);
    }

    protected String getRawPath() {
        return this.path;
    }
}

