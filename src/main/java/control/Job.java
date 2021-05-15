package control;

import control.executorhandler.ExecutorHandler;
import download.Category;
import download.DownloadActions;
import download.Downloadable;
import download.QueueStatus;
import download.methods.DownloadMethod;

import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

public class Job {
    private static final Logger logger = Logger.getLogger(Job.class.getName());
    private final String name, description;
    private final DownloadMethod method;
    private final DownloadActions actions;
    private final Set<Category> categories;
    private final QueueStatus status = new QueueStatus();

    public Job(String name, String description, DownloadMethod method, DownloadActions actions, Set<String> categories) {
        if (method == null)
            throw new IllegalArgumentException("DownloadMethod method can not be null!");

        if (actions == null)
            throw new IllegalArgumentException("Set DownloadAction action can not be null!");

        if (categories == null || categories.isEmpty())
            throw new IllegalArgumentException("Category List can not be empty or null!");

        this.method = method;
        this.actions = actions;
        this.description = description;
        this.name = name;
        this.categories = Category.getCategories(categories);
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
        if(status.addWhenNotQueued()) {
            logger.fine(() -> "Submitted Job name: " + this.getName() + " ; description : " + this.getDescription());
            ExecutorHandler.submit(new Downloadable(method, actions, categories, status));
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return Objects.equals(name, job.name) && Objects.equals(description, job.description)
                && method.equals(job.method) && actions.equals(job.actions)
                && categories.equals(job.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, method, actions, categories);
    }
}
