package com.code.examples.intro;

import com.code.examples.intro.solutions.LRUCacheCustom;
import com.code.examples.intro.solutions.LRUCacheLinkedHashMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests that verify both LRU Cache implementations behave identically
 * under the same sequence of operations.
 */
class LRUCacheTest {

    // ─── Custom cache tests ──────────────────────────────────

    @Test
    void customCache_basicEviction() {
        LRUCacheCustom cache = new LRUCacheCustom(3);

        cache.put(1, 10);
        cache.put(2, 20);
        cache.put(3, 30);

        // access key 1 so it becomes MRU; order is now 2 → 3 → 1
        assertEquals(10, cache.get(1));

        // insert 4 → evicts LRU which is key 2
        cache.put(4, 40);

        assertEquals(-1, cache.get(2), "key 2 should have been evicted");
        assertEquals(10, cache.get(1));
        assertEquals(30, cache.get(3));
        assertEquals(40, cache.get(4));
    }

    @Test
    void customCache_updateExistingKey() {
        LRUCacheCustom cache = new LRUCacheCustom(2);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(1, 10);          // update key 1 → promotes it to MRU
        cache.put(3, 3);           // evicts key 2 (LRU)

        assertEquals(-1, cache.get(2));
        assertEquals(10, cache.get(1));
        assertEquals(3, cache.get(3));
    }

    @Test
    void customCache_capacityOne() {
        LRUCacheCustom cache = new LRUCacheCustom(1);
        cache.put(1, 1);
        assertEquals(1, cache.get(1));
        cache.put(2, 2);           // evicts key 1
        assertEquals(-1, cache.get(1));
        assertEquals(2, cache.get(2));
    }

    // ─── LinkedHashMap cache tests ───────────────────────────

    @Test
    void linkedHashMapCache_basicEviction() {
        LRUCacheLinkedHashMap cache = new LRUCacheLinkedHashMap(3);

        cache.putInCache(1, 10);
        cache.putInCache(2, 20);
        cache.putInCache(3, 30);

        assertEquals(10, cache.getFromCache(1));

        cache.putInCache(4, 40);

        assertEquals(-1, cache.getFromCache(2), "key 2 should have been evicted");
        assertEquals(10, cache.getFromCache(1));
        assertEquals(30, cache.getFromCache(3));
        assertEquals(40, cache.getFromCache(4));
    }

    @Test
    void linkedHashMapCache_updateExistingKey() {
        LRUCacheLinkedHashMap cache = new LRUCacheLinkedHashMap(2);
        cache.putInCache(1, 1);
        cache.putInCache(2, 2);
        cache.putInCache(1, 10);   // update
        cache.putInCache(3, 3);    // evicts key 2

        assertEquals(-1, cache.getFromCache(2));
        assertEquals(10, cache.getFromCache(1));
        assertEquals(3, cache.getFromCache(3));
    }

    // ─── Parity test: both caches produce the same results ──

    @Test
    void bothCaches_produceSameResults() {
        LRUCacheCustom       custom = new LRUCacheCustom(3);
        LRUCacheLinkedHashMap lhm    = new LRUCacheLinkedHashMap(3);

        int[][] puts = {{1, 10}, {2, 20}, {3, 30}};
        for (int[] p : puts) {
            custom.put(p[0], p[1]);
            lhm.putInCache(p[0], p[1]);
        }

        // access key 1
        assertEquals(custom.get(1), lhm.getFromCache(1));

        // insert key 4 → evict LRU
        custom.put(4, 40);
        lhm.putInCache(4, 40);

        // verify identical behaviour for every key
        for (int key = 1; key <= 4; key++) {
            assertEquals(custom.get(key), lhm.getFromCache(key),
                    "Mismatch for key " + key);
        }
    }
}
