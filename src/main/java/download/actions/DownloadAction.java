package download.actions;

import download.Category;

public interface DownloadAction {

    /**
     * Performs the recurring action for each category
     * @param data byte[] with data
     * @param category current Category
     * @return true when everything successful else false
     */
    boolean action(byte[] data, Category category);

    /**
     * Performs the init
     * @return true when all successful
     */
    boolean init();

    /**
     * Performs the finish
     * @return true when all successful
     */
    boolean finish();

}
