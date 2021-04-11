package control;

import control.executorhandler.ExecutorHandler;
import download.Category;
import download.Downloadable;
import download.actions.DownloadAction;
import download.actions.DownloadActions;
import download.methods.DownloadMethod;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class Job {

    private static final Logger logger = Logger.getGlobal();
    private final String name, description;
    private final DownloadMethod method;
    private final Set<DownloadAction> actions;
    private final Set<Category> categories = new HashSet<>();

    public Job(String name, String description, DownloadMethod method, Set<DownloadAction> actions, Set<String> categories) {
        if (method == null)
            throw new IllegalArgumentException("DownloadMethod method can not be null!");

        if (actions == null)
            throw new IllegalArgumentException("Set DownloadAction action can not be null!");

        if (categories == null || categories.isEmpty())
            throw new IllegalArgumentException("Category List can not be empty!");

        this.method = method;
        this.actions = actions;
        this.description = description;
        this.name = name;

        for (String str : categories) {
            this.categories.add(new Category(str));
        }
    }

    /**
     * Checks if a Job shall be downloaded again.
     *
     * @return true only when a job shall be downloaded again
     */
    public boolean check() {
        return method.check(categories);
    }

    /**
     * Submits the Job for Download when it is necessary
     */
    public void submitIfnNecessary() {
        if (check()) {
            submit();
        }
    }

    /**
     * Submits a new Downloadable from this Job for execution
     */
    public void submit() {
        ExecutorHandler.submit(new Downloadable(method, new DownloadActions(actions), categories));
    }

    /**
     * Gets the description of this job
     *
     * @return description
     */
    public String getDescription() {
        if (description == null) {
            return "";
        }

        return description;
    }

    /**
     * Gets the name of this job
     *
     * @return name
     */
    public String getName() {
        if (name == null) {
            return "";
        }

        return name;
    }

}
