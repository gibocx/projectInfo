package control.wrappers;

import control.Job;
import download.actions.DownloadAction;
import download.actions.DownloadActions;
import download.methods.DownloadMethodFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class JobWrapper {
    private String name, description;
    private final Map<String, String> download = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private Set<String> categories;
    private Set<ActionWrapper> actions;

    Job toJob() {
        return new Job(name, description, DownloadMethodFactory.newMethod(download),
                getDownloadActions(), categories);
    }

    private DownloadActions getDownloadActions() {
        Set<DownloadAction> out = new HashSet<>();

        for (ActionWrapper action : actions) {
            out.add(action.getAction());
        }

        return new DownloadActions(out);
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
}
