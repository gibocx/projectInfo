package download.actions;

import download.Category;
import utility.FileStuff;
import utility.Placeholders;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ActionSaveToZip extends ActionSaveToFile {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final String zipEntryName;

    public ActionSaveToZip(String file, String zipEntryName) {
        super(file);

        if (zipEntryName == null || zipEntryName.trim().isEmpty()) {
            throw new IllegalArgumentException("zipEntryName can not be empty!");
        }

        this.zipEntryName = zipEntryName;

        if (!file.endsWith(".zip")) {
            logger.warning(() -> "Zip entry does not end in .zip! " + zipEntryName);
        }
    }

    @Override
    public boolean action(byte[] data, Category category) {
        String file = super.computePath();

        try {
            if (FileStuff.createFile(file)) {
                ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));
                ZipEntry e = new ZipEntry(Placeholders.replace(zipEntryName, category.getName()));
                out.putNextEntry(e);

                out.write(data);
                out.closeEntry();
                out.close();
                return true;
            }
        } catch (IOException ex) {
            FileStuff.delete(file);
            logger.info("IOException in ActionSaveToFileZip " + ex.getMessage());
        }
        return false;
    }

    public String getZipEntryName(String cat) {
        return Placeholders.replace(zipEntryName, cat);
    }
}
