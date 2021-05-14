package control.wrappers;

import control.Job;
import download.DownloadActions;
import download.methods.DownloadMethodFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;


public class JobWrapper {
    private final Map<String, String> download = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private String name, description;
    private Set<String> categories;
    private Set<ActionWrapper> actions;
    private List<PreActionWrapper> preActions;

    Job toJob() {
        return new Job(name, description, DownloadMethodFactory.newMethod(download),
                getDownloadActions(), categories);
    }

    private DownloadActions getDownloadActions() {
        return new DownloadActions(ActionWrapper.getActions(actions), PreActionWrapper.getPreActions(preActions));
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

    public void setPreAction(List<PreActionWrapper> preActions) {
        this.preActions = preActions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobWrapper that = (JobWrapper) o;
        return Objects.equals(download, that.download) && Objects.equals(categories, that.categories) && Objects.equals(actions, that.actions) && Objects.equals(preActions, that.preActions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(download, categories, actions, preActions);
    }
}
