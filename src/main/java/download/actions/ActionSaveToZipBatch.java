package download.actions;

import control.wrappers.ActionWrapper;
import download.Category;
import utility.FileStuff;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class ActionSaveToZipBatch extends ActionSaveToZip {
    private static final Logger logger = Logger.getLogger(ActionSaveToZipBatch.class.getName());
    private ZipOutputStream out;

    public ActionSaveToZipBatch(String filePath, String zipEntryName) {
        super(filePath, zipEntryName);

    }

    public ActionSaveToZipBatch(final ActionWrapper action) {
        super(action.getNullable("filePath"), action.getNullable("zipEntryName"));
    }

    @Override
    public boolean action(final byte[] data, final Category category) {
        try {
            if (out != null) {
                ZipEntry e = new ZipEntry(super.getZipEntryName(category));
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

        if (FileStuff.createFile(filePath)) {
            out = getZipStream(filePath);
        } else {
            logger.warning(() -> "Unable to create file with path " + filePath);
        }

        return (out != null);
    }

    @Override
    public boolean finish() {
        try {
            out.close();
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Unable to close ZipedOutputStream!", ex);
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionSaveToZipBatch that = (ActionSaveToZipBatch) o;
        return Objects.equals(out, that.out);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), out);
    }
}
