package download.actions;

import control.wrappers.ActionWrapper;
import download.Category;
import utility.FileStuff;
import utility.Placeholders;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class ActionSaveToZip extends ActionSaveToFile {
    private static final Logger logger = Logger.getLogger(ActionSaveToZip.class.getName());
    private final String zipEntryName;

    public ActionSaveToZip(final String file, final String zipEntryName) {
        super(file);

        if (zipEntryName == null || zipEntryName.trim().isEmpty()) {
            throw new IllegalArgumentException("zipEntryName can not be empty!");
        }

        this.zipEntryName = zipEntryName;

        if (!file.endsWith(".zip")) {
            logger.warning(() -> "Zip entry does not end in .zip! " + zipEntryName);
        }
    }

    public ActionSaveToZip(final ActionWrapper action) {
        this(action.getNullable("file"), action.getNullable("zipEntryName"));
    }

    @Override
    public boolean action(final byte[] data, final Category category) {
        String file = super.computePath();

        try {
            if (FileStuff.createFile(file)) {
                ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));
                ZipEntry e = new ZipEntry(Placeholders.replace(zipEntryName, category));
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

    public String getZipEntryName(final Category category) {
        return Placeholders.replace(zipEntryName, category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionSaveToZip that = (ActionSaveToZip) o;
        return Objects.equals(zipEntryName, that.zipEntryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), zipEntryName);
    }
}
