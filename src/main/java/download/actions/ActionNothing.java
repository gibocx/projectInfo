package download.actions;

import download.Category;

/**
 * This DownloadAction is meant for testing.
 * Every function returns successful and the
 * Constructor can be constructed with any arguments!
 */
public class ActionNothing implements DownloadAction {

    public ActionNothing() {
    }

    public ActionNothing(Object... args) {
    }

    /**
     * Performs the recurring action for each category
     *
     * @param data     byte[] with data
     * @param category current Category
     * @return true when everything successful else false
     */
    @Override
    public boolean action(byte[] data, Category category) {
        return true;
    }

    /**
     * Performs the init
     *
     * @return true when all successful
     */
    @Override
    public boolean init() {
        return true;
    }

    /**
     * Performs the finish
     *
     * @return true when all successful
     */
    @Override
    public boolean finish() {
        return true;
    }
}