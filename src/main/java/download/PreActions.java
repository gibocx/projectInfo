package download;

import download.preactions.PreAction;
import utility.Cache;
import utility.CalcChecksum;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class PreActions implements PreAction {
    private static final Cache<Long, byte[]> cache = new Cache<>();
    private static final Logger logger = Logger.getLogger(PreActions.class.getName());
    private final List<PreAction> preActions;

    /**
     * Uses the default PreAction Nothing
     *
     * @param actions
     */
    public PreActions(List<PreAction> actions) {
        this.preActions = actions;
    }

    public boolean putIfNotPresent(byte[] data) {
        long checksum = CalcChecksum.checksum(data);

        if(!cache.containsKey(checksum)) {
            populateCache(data, checksum);
        }
        data = cache.get(checksum).orElse(new byte[0]);
        return true;
    }

    private void populateCache(byte[] data, long checksum) {
        for(PreAction preAction : preActions) {
            preAction.compute(data);
        }

        cache.put(checksum, data);
    }

    @Override
    public boolean compute(byte[] data) {
        return putIfNotPresent(data);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreActions that = (PreActions) o;
        return Objects.equals(preActions, that.preActions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(preActions);
    }
}
