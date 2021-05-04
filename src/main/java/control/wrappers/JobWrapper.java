package control.wrappers;

import control.Job;
import download.actions.DownloadActions;
import download.methods.DownloadMethodFactory;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;


public class JobWrapper {
    private final Map<String, String> download = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private String name, description;
    private Set<String> categories;
    private Set<ActionWrapper> actions;
    private PreActionWrapper preAction;

    Job toJob() {
        return new Job(name, description, DownloadMethodFactory.newMethod(download),
                getDownloadActions(), categories);
    }

    private DownloadActions getDownloadActions() {
        return new DownloadActions(ActionWrapper.getActions(actions), preAction.getPreAction());
    }

    private void setActions(Set<ActionWrapper> actions) {
        this.actions = actions;
    }

    public void setDownload(Map<String, String> download) {
        this.download.putAll(download);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public void setPreAction(PreActionWrapper preAction) {
        this.preAction = preAction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobWrapper that = (JobWrapper) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description)
                && download.equals(that.download) && categories.equals(that.categories) && actions.equals(that.actions)
                && preAction.equals(that.preAction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(download, categories, actions, preAction);
    }
}
