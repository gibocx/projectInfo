package utility;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Cache<K, V> {
    public static final Long STD_TIME_TO_LIVE_MS = 300000L;
    private Map<K, CacheValue<V>> map;
    private final Long normalTimeToLive;

    public Cache() {
        this(STD_TIME_TO_LIVE_MS);
    }

    /**
     * @param normalTimeToLive standard TTL in milliseconds
     */
    public Cache(Long normalTimeToLive) {
        this.normalTimeToLive = normalTimeToLive;
        this.clear();
    }

    public void clean() {
        for(K key: getExpiredKeys()) {
            remove(key);
        }
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    public void clear() {
        map = new HashMap<>();
    }

    public Optional<V> get(K key) {
        clean();
        return Optional.ofNullable(map.get(key)).map(CacheValue::getValue);
    }

    /**
     * @param key key for retrieving the value
     * @param value Object to store
     * @param timeToLive TTL for the object to live in milliseconds
     */
    public void put(K key, V value, long timeToLive) {
        if(value == null) {
            remove(key);
        }

        map.put(key, new CacheValue<V>(value, timeToLive));
    }

    public void put(K key, V value) {
        put(key,value, normalTimeToLive);
    }

    public void remove(K key) {
        map.remove(key);
    }

    private Set<K> getExpiredKeys() {
        return map.keySet().parallelStream()
                .filter(this::isExpired)
                .collect(Collectors.toSet());
    }

    private boolean isExpired(K key) {
        return (map.get(key).validUntil() < Instant.now().toEpochMilli());
    }

    private static class CacheValue<V> {
        private final V value;
        private final long validUntil;

        /**
         * @param value Object to store
         * @param timeToLive TTL of the object in MS
         */
        protected CacheValue(V value, long timeToLive) {
            this.value = value;
            this.validUntil = Instant.now().toEpochMilli() + timeToLive;
        }

        protected V getValue() {
            return value;
        }

        protected long validUntil() {
            return validUntil;
        }
    }
}
