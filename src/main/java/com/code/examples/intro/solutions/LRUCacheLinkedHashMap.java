package com.code.examples.intro.solutions;

import java.util.*;

/**
 * LRU Cache implementation using Java's LinkedHashMap.
 *
 * LinkedHashMap with accessOrder=true automatically moves entries to the tail
 * on every get/put. By overriding removeEldestEntry we cap the size, evicting
 * the head (least-recently-used) entry when capacity is exceeded.
 *
 * Time complexity: O(1) for get and put (amortised).
 * Space complexity: O(capacity).
 */
public class LRUCacheLinkedHashMap extends LinkedHashMap<Integer, Integer> {

    private final int capacity;

    public LRUCacheLinkedHashMap(int capacity) {
        // accessOrder = true → maintains access order instead of insertion order
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    /**
     * Called after every put/putAll. When size exceeds capacity the eldest
     * (least-recently-used) entry is automatically removed.
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }

    // ─── convenience wrappers to match the custom cache API ───

    public int getFromCache(int key) {
        return getOrDefault(key, -1);
    }

    public void putInCache(int key, int value) {
        put(key, value);
    }
}
