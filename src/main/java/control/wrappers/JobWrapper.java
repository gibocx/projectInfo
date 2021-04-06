package control.wrappers;

import control.Job;
import download.actions.DownloadAction;
import download.methods.DownloadMethodFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JobWrapper {
    private String name, description;
    private Map<String, String> download;
    private Set<String> categories;
    private Set<ActionWrapper> actions;

    Job toJob() {
        return new Job(name, description, DownloadMethodFactory.newMethod(download),
                computeActions(), categories);
    }

    private Set<DownloadAction> computeActions() {
        ActionWrapper[] tmp = new ActionWrapper[actions.size()];
        Set<DownloadAction> out = new HashSet<>();

        actions.toArray(tmp);

        for(ActionWrapper action : tmp) {
            out.add(action.getAction());
        }

        return out;
    }

    private void setActions(Set<ActionWrapper> actions) {
        this.actions = actions;
    }

    public void setDownload(Map<String, String> download) {
        this.download = download;
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
