package download.actions;

import download.Category;
import utility.FileStuff;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class ActionSaveToZipBatch extends ActionSaveToZip {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private ZipOutputStream out;

    public ActionSaveToZipBatch(String filePath, String zipEntryName) {
        super(filePath, zipEntryName);

    }

    public ActionSaveToZipBatch(Map<String, String> data) {
        super(data.get("filePath"), data.get("zipEntryName"));
    }

    @Override
    public boolean action(byte[] data, Category category) {
        try {
            if (out != null) {
                ZipEntry e = new ZipEntry(super.getZipEntryName(category.getName()));
                out.putNextEntry(e);

                out.write(data);
                out.closeEntry();

                return true;
            }
        } catch (IOException ex) {
            logger.info("IOException in ActionSaveToFileZipBatch " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean init() {
        String filePath = super.computePath();
        try {
            if (FileStuff.createFile(filePath)) {
                this.out = new ZipOutputStream(new FileOutputStream(filePath));
                return true;
            } else {
                logger.warning(() -> "unable to create file with path " + filePath);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean finish() {
        try {
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return true;
    }
}
