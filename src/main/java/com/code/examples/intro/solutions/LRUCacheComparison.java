package com.code.examples.intro.solutions;

/**
 * Side-by-side comparison driver for the two LRU Cache implementations.
 *
 * Runs the exact same operations on both caches and prints their state after
 * each step so you can verify identical eviction behaviour.
 */
public class LRUCacheComparison {

    public static void main(String[] args) {

        LRUCacheLinkedHashMap lhm = new LRUCacheLinkedHashMap(3);
        LRUCacheCustom       cst = new LRUCacheCustom(3);

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║           LRU Cache — LinkedHashMap vs Custom                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();

        // ── Step 1 : put(1,10), put(2,20), put(3,30) ─────────────────
        step("put(1,10); put(2,20); put(3,30)", () -> {
            lhm.putInCache(1, 10); lhm.putInCache(2, 20); lhm.putInCache(3, 30);
            cst.put(1, 10);        cst.put(2, 20);        cst.put(3, 30);
        }, lhm, cst);

        // ── Step 2 : get(1)  →  key 1 becomes MRU ────────────────────
        step("get(1)  — promotes key 1 to MRU", () -> {
            lhm.getFromCache(1);
            cst.get(1);
        }, lhm, cst);

        // ── Step 3 : put(4,40)  →  should evict key 2 (LRU) ──────────
        step("put(4,40) — should evict key 2 (LRU)", () -> {
            lhm.putInCache(4, 40);
            cst.put(4, 40);
        }, lhm, cst);

        // ── Step 4 : get(2) → should return -1 / null ─────────────────
        int lhmVal = lhm.getFromCache(2);
        int cstVal = cst.get(2);
        System.out.println("Step 4 │ get(2) after eviction");
        System.out.printf("       │  LinkedHashMap → %d%n", lhmVal);
        System.out.printf("       │  Custom        → %d%n", cstVal);
        System.out.println();

        // ── Step 5 : put(5,50) → should evict key 3 ──────────────────
        step("put(5,50) — should evict key 3 (LRU)", () -> {
            lhm.putInCache(5, 50);
            cst.put(5, 50);
        }, lhm, cst);

        // ── Benchmark ─────────────────────────────────────────────────
        System.out.println("───────────────────────────────────────────────────────────────");
        System.out.println("Micro-benchmark  (1 000 000 put + get ops, capacity = 1000)");
        System.out.println("───────────────────────────────────────────────────────────────");

        int ops = 1_000_000;
        int cap = 1000;

        // warm-up
        benchmarkLinkedHashMap(ops, cap);
        benchmarkCustom(ops, cap);

        long t1 = benchmarkLinkedHashMap(ops, cap);
        long t2 = benchmarkCustom(ops, cap);

        System.out.printf("  LinkedHashMap : %,d ms%n", t1);
        System.out.printf("  Custom (DLL)  : %,d ms%n", t2);
        System.out.println();
    }

    // ─── helpers ─────────────────────────────────────────────

    private static int stepCounter = 0;

    private static void step(String description, Runnable action,
                             LRUCacheLinkedHashMap lhm, LRUCacheCustom cst) {
        stepCounter++;
        action.run();
        System.out.printf("Step %d │ %s%n", stepCounter, description);
        System.out.printf("       │  LinkedHashMap → %s%n", lhm);
        System.out.printf("       │  Custom        → %s%n", cst);
        System.out.println();
    }

    private static long benchmarkLinkedHashMap(int ops, int capacity) {
        LRUCacheLinkedHashMap cache = new LRUCacheLinkedHashMap(capacity);
        long start = System.nanoTime();
        for (int i = 0; i < ops; i++) {
            cache.putInCache(i, i);
            cache.getFromCache(i / 2);
        }
        return (System.nanoTime() - start) / 1_000_000;
    }

    private static long benchmarkCustom(int ops, int capacity) {
        LRUCacheCustom cache = new LRUCacheCustom(capacity);
        long start = System.nanoTime();
        for (int i = 0; i < ops; i++) {
            cache.put(i, i);
            cache.get(i / 2);
        }
        return (System.nanoTime() - start) / 1_000_000;
    }
}
