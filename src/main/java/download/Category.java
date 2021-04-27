package download;

import java.util.HashSet;
import java.util.Set;

public class Category {
    private final String name;
    private long checksum, lastDownloaded;

    public Category(String name) {
        this.name = name;
        this.checksum = 0;
        this.lastDownloaded = 0;
    }

    public static Set<Category> getCategories(Set<String> categories) {
        Set<Category> set = new HashSet<>();
        categories.forEach(str -> set.add(new Category(str)));

        return set;
    }

    public long getChecksum() {
        return this.checksum;
    }

    public void setChecksum(long checksum) {
        this.checksum = checksum;
    }

    public long getLastDownloaded() {
        return lastDownloaded;
    }

    public void setLastDownloaded(long lastDownloaded) {
        if (lastDownloaded < 0) {
            throw new IllegalArgumentException("lastDownloaded can not be negative!");
        }
        this.lastDownloaded = lastDownloaded;
    }

    public String getName() {
        return name;
    }

}