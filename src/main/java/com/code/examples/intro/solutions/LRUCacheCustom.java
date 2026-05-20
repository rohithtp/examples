package com.code.examples.intro.solutions;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * LRU Cache implemented from scratch using a doubly-linked list + HashMap.
 *
 * Design
 * ------
 *   ┌──────┐   prev   ┌──────┐   prev   ┌──────┐
 *   │ HEAD │ ◄──────── │ node │ ◄──────── │ TAIL │
 *   │(dummy)│ ────────►│      │ ────────►│(dummy)│
 *   └──────┘   next    └──────┘   next    └──────┘
 *
 *   HEAD.next  → most-recently-used
 *   TAIL.prev  → least-recently-used   (eviction candidate)
 *
 * Wait — that's the *opposite* of the usual convention. Let's use the standard:
 *   HEAD side  = least-recently-used  (evict from here)
 *   TAIL side  = most-recently-used   (promote here)
 *
 * Time complexity : O(1) for get and put.
 * Space complexity: O(capacity).
 */
public class LRUCacheCustom {

    // ─── inner node ──────────────────────────────────────────

    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    // ─── fields ──────────────────────────────────────────────

    private final int capacity;
    private final Map<Integer, Node> map;
    private final Node head; // dummy head – next points to LRU node
    private final Node tail; // dummy tail – prev points to MRU node

    // ─── constructor ─────────────────────────────────────────

    public LRUCacheCustom(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();

        // sentinel nodes simplify add/remove edge-cases
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    // ─── public API ──────────────────────────────────────────

    /**
     * Returns the value for the given key, or -1 if absent.
     * Accessing a key promotes it to most-recently-used.
     */
    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }
        // promote to MRU position (just before tail)
        moveToTail(node);
        return node.value;
    }

    /**
     * Inserts or updates the key-value pair.
     * If the cache is at capacity the least-recently-used entry is evicted.
     */
    public void put(int key, int value) {
        Node existing = map.get(key);

        if (existing != null) {
            // update value and promote
            existing.value = value;
            moveToTail(existing);
        } else {
            // evict LRU if full
            if (map.size() == capacity) {
                Node lru = head.next;       // LRU is right after dummy head
                removeNode(lru);
                map.remove(lru.key);
            }
            // insert new node at MRU position
            Node newNode = new Node(key, value);
            addBeforeTail(newNode);
            map.put(key, newNode);
        }
    }

    // ─── linked-list helpers ─────────────────────────────────

    /** Detach a node from the list. */
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    /** Insert a node just before the dummy tail (MRU position). */
    private void addBeforeTail(Node node) {
        node.prev = tail.prev;
        node.next = tail;
        tail.prev.next = node;
        tail.prev = node;
    }

    /** Move an existing node to the MRU position. */
    private void moveToTail(Node node) {
        removeNode(node);
        addBeforeTail(node);
    }

    // ─── diagnostics ─────────────────────────────────────────

    /** Returns the current number of entries. */
    public int size() {
        return map.size();
    }

    /** Pretty-prints the cache from LRU → MRU order. */
    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "{", "}");
        Node curr = head.next;
        while (curr != tail) {
            sj.add(curr.key + "=" + curr.value);
            curr = curr.next;
        }
        return sj.toString();
    }
}
