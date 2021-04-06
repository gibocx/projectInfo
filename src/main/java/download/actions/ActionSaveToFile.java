package download.actions;

import download.Category;
import utility.FileStuff;
import utility.Placeholders;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class ActionSaveToFile implements DownloadAction {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final String path;

    public ActionSaveToFile(String path) {
        if (path != null && !path.trim().isEmpty()) {
            this.path = path;
        } else {
            throw new IllegalArgumentException("filePath can not be empty!");
        }
    }

    public boolean action(byte[] data, Category category) {
        if(data == null || category == null) {
            return false;
        }

        String compPath = computePath(category.getName());

        try {
            if (FileStuff.createFile(compPath)) {
                FileOutputStream outputStream = new FileOutputStream(compPath);
                outputStream.write(data);
                outputStream.close();
                return true;
            }
        } catch (IOException ex) {
            FileStuff.delete(compPath);
            logger.info("IOException in ActionSaveToFile " + ex.getMessage());
        }

        return false;
    }

    String computePath(String category) {
        return Placeholders.replace(this.path, category);
    }

    String computePath() {
        return Placeholders.replace(this.path);
    }

    public boolean init() {
        return true;
    }

    public boolean finish() {
        return true;
    }

    protected String getRawPath() {
        return this.path;
    }

}

