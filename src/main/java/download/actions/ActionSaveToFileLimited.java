package download.actions;

import control.wrappers.ActionWrapper;
import download.Category;

import java.io.File;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

class ActionSaveToFileLimited extends ActionSaveToFile {
    private static final Logger logger = Logger.getLogger(ActionSaveToFileLimited.class.getName());
    private final int maxFiles;

    public ActionSaveToFileLimited(final String path, final int maxFiles) {
        super(path);

        if (maxFiles <= 0) {
            throw new IllegalArgumentException("maxFiles can not be less or equal to 0; maxFiles was " + maxFiles);
        }

        this.maxFiles = maxFiles;
    }

    public ActionSaveToFileLimited(final ActionWrapper action) {
        this(action.getNullable("path"), Integer.parseInt(action.get("maxFiles").orElse("0")));
    }

    public boolean action(final byte[] data, final Category category) {
        File f = new File(super.computePath(category));

        try {
            if (new File(f.getParent()).listFiles().length <= maxFiles) {
                return super.action(data, category);
            }
        } catch (NullPointerException ex) {
            logger.log(Level.INFO, "Unable to get the number of files", ex);
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionSaveToFileLimited that = (ActionSaveToFileLimited) o;
        return maxFiles == that.maxFiles;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maxFiles);
    }
}
