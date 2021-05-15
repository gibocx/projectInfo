package utility;

import control.executorhandler.ExecutorHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Cache<K, V> {
    public static final Long STD_TIME_TO_LIVE_MS = 30_000L;
    public static final int STD_CLEAN_PERIOD_MS = 3_000;
    private static final Logger logger = Logger.getLogger(Cache.class.getName());
    private final Map<K, CacheValue<V>> map = new HashMap<>();
    private final Long normalTimeToLive;

    public Cache() {
        this(STD_TIME_TO_LIVE_MS);
    }

    public Cache(final Long normalTimeToLive, final int cleanPeriod) {
        if(normalTimeToLive > 0) {
            this.normalTimeToLive = normalTimeToLive;
        } else {
            logger.info(() -> "normalTimeToLive must be greater than 0 MS, was " + normalTimeToLive + "; set " +
                    "to STD_TIME_TO_LIVE_MS : " + STD_TIME_TO_LIVE_MS);
            this.normalTimeToLive = STD_TIME_TO_LIVE_MS;
        }

        if(cleanPeriod > 0) {
            ExecutorHandler.scheduleAtFixedRate(this::clean,cleanPeriod,cleanPeriod);
        } else {
            logger.info(() -> "cleanPeriod has to be greater than 0 MS to enable autoclean " + cleanPeriod);
        }
    }

    /**
     * @param normalTimeToLive standard TTL in milliseconds
     */
    public Cache(final Long normalTimeToLive) {
        this(normalTimeToLive, STD_CLEAN_PERIOD_MS);
    }

    public void clean() {
        for (K key : getExpiredKeys()) {
            remove(key);
        }
    }

    public boolean containsKey(final K key) {
        return map.containsKey(key);
    }

    public void clear() {
        map.clear();
    }

    public Optional<V> get(final K key) {
        return Optional.ofNullable(map.get(key)).map(CacheValue::getValue);
    }

    /**
     * @param key        key for retrieving the value
     * @param value      Object to store
     * @param timeToLive TTL for the object to live in milliseconds
     */
    public void put(final K key, final V value, final long timeToLive) {
        if (value == null) {
            remove(key);
        }

        map.put(key, new CacheValue<>(value, timeToLive));
        clean();
    }

    public void put(final K key, final V value) {
        put(key, value, normalTimeToLive);
    }

    public void remove(final K key) {
        map.remove(key);
    }

    private Set<K> getExpiredKeys() {
        return map.keySet().parallelStream()
                .filter(this::isExpired)
                .collect(Collectors.toSet());
    }

    private boolean isExpired(final K key) {
        return (map.get(key).validUntil() < Instant.now().toEpochMilli());
    }

    private static class CacheValue<V> {
        private final V value;
        private final long validUntil;

        /**
         * @param value      Object to store
         * @param timeToLive TTL of the object in MS
         */
        protected CacheValue(final V value, final long timeToLive) {
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
