package com.code.examples.thread;

import java.util.concurrent.*;

/**
 * ============================================================================
 * REAL-LIFE PRODUCER-CONSUMER EXAMPLE: RESTAURANT ORDER PROCESSING SYSTEM
 * ============================================================================
 *
 * Scenario:
 * ---------
 * Imagine a busy restaurant kitchen. Waiters take orders from customers and
 * place them on a shared "order board" (the BlockingQueue). Chefs pick up
 * orders from the board and prepare the food.
 *
 * - PRODUCER (Waiter):  Takes customer orders and places them on the order board.
 * - CONSUMER (Chef):    Picks orders from the board and prepares the meals.
 * - BLOCKING QUEUE:     The shared order board with LIMITED capacity (max 3 orders
 *                       at a time — the board is small!).
 *
 * Why BlockingQueue?
 * ------------------
 * 1. If the order board is FULL (3 orders pending), the waiter WAITS (blocks)
 *    until a chef picks up an order and frees a slot.
 * 2. If the order board is EMPTY, the chef WAITS (blocks) until a waiter
 *    places a new order.
 * 3. This is THREAD-SAFE — no explicit synchronization or locks needed.
 *
 * Interaction Limit:
 * ------------------
 * The waiter will place exactly 10 orders. Once the chef processes all 10,
 * both threads terminate gracefully using a POISON PILL pattern.
 *
 * ============================================================================
 */
public class RestaurantOrderSystem {

    // ─────────────────────────────────────────────────────────────────────────
    // The Order Board: A bounded BlockingQueue with capacity 3.
    //
    // ArrayBlockingQueue is backed by a fixed-size array.
    // - Bounded: prevents memory overflow if the producer is faster.
    // - FIFO ordering: orders are processed in the sequence they arrive.
    // - Thread-safe: internally uses ReentrantLock + Condition variables.
    // ─────────────────────────────────────────────────────────────────────────
    private static final int ORDER_BOARD_CAPACITY = 3;
    private static final BlockingQueue<String> orderBoard =
            new ArrayBlockingQueue<>(ORDER_BOARD_CAPACITY);

    // ─────────────────────────────────────────────────────────────────────────
    // Poison Pill: A special sentinel value that signals the consumer to stop.
    //
    // Why not use Thread.interrupt()?
    //   - Because the consumer is blocked on queue.take(), and while interrupt
    //     does unblock it, using a poison pill is a cleaner, more explicit
    //     shutdown pattern that doesn't rely on exception handling for control flow.
    // ─────────────────────────────────────────────────────────────────────────
    private static final String POISON_PILL = "KITCHEN_CLOSED";

    // Total number of real orders to process
    private static final int TOTAL_ORDERS = 10;

    // Simulated menu items
    private static final String[] MENU = {
        "Butter Chicken",
        "Paneer Tikka",
        "Biryani",
        "Masala Dosa",
        "Chole Bhature",
        "Rogan Josh",
        "Tandoori Roti",
        "Dal Makhani",
        "Gulab Jamun",
        "Mango Lassi"
    };

    public static void main(String[] args) throws InterruptedException {

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  🍽️  RESTAURANT ORDER PROCESSING SYSTEM");
        System.out.println("  Order Board Capacity: " + ORDER_BOARD_CAPACITY);
        System.out.println("  Total Orders: " + TOTAL_ORDERS);
        System.out.println("═══════════════════════════════════════════════════════\n");

        // ─────────────────────────────────────────────────────────────────────
        // PRODUCER THREAD — The Waiter
        //
        // Responsibilities:
        //   1. Takes customer orders (simulated from the MENU array).
        //   2. Places each order on the order board using queue.put().
        //   3. queue.put() BLOCKS if the board is full (capacity = 3).
        //   4. After placing all 10 orders, sends a POISON_PILL to signal
        //      the chef that no more orders are coming.
        //
        // Thread.sleep(300ms) simulates the time a waiter takes to walk
        // from the table to the kitchen and write the order on the board.
        // ─────────────────────────────────────────────────────────────────────
        Runnable waiter = () -> {
            try {
                for (int i = 0; i < TOTAL_ORDERS; i++) {
                    String order = "Order #" + (i + 1) + ": " + MENU[i];

                    System.out.println("🧑‍🍳 [WAITER]   Placing → " + order
                            + "  (Board: " + orderBoard.size() + "/" + ORDER_BOARD_CAPACITY + ")");

                    // ┌──────────────────────────────────────────────────────┐
                    // │  queue.put(order)                                    │
                    // │                                                      │
                    // │  • Inserts the order into the queue.                 │
                    // │  • If the queue is FULL, this call BLOCKS the        │
                    // │    waiter thread until space becomes available.      │
                    // │  • This is what makes it "blocking" — no busy-wait, │
                    // │    no polling, no CPU waste. The thread parks.       │
                    // └──────────────────────────────────────────────────────┘
                    orderBoard.put(order);

                    System.out.println("✅ [WAITER]   Placed  ✓ " + order
                            + "  (Board: " + orderBoard.size() + "/" + ORDER_BOARD_CAPACITY + ")");

                    Thread.sleep(300); // Simulate walking back to take the next order
                }

                // ┌──────────────────────────────────────────────────────────┐
                // │  POISON PILL — Graceful Shutdown Signal                  │
                // │                                                          │
                // │  After all orders are placed, we insert a special        │
                // │  sentinel value. When the chef picks this up, it knows   │
                // │  the kitchen is closing and exits its loop.              │
                // └──────────────────────────────────────────────────────────┘
                orderBoard.put(POISON_PILL);
                System.out.println("\n🚪 [WAITER]   All orders placed. Kitchen closing signal sent.");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("❌ [WAITER] Interrupted: " + e.getMessage());
            }
        };

        // ─────────────────────────────────────────────────────────────────────
        // CONSUMER THREAD — The Chef
        //
        // Responsibilities:
        //   1. Picks orders from the order board using queue.take().
        //   2. queue.take() BLOCKS if the board is empty (no orders to cook).
        //   3. Prepares the order (simulated with Thread.sleep(800ms) — cooking
        //      takes longer than placing an order, so the board fills up!).
        //   4. Stops when it receives the POISON_PILL.
        //
        // KEY OBSERVATION:
        //   The chef (consumer) is SLOWER than the waiter (producer).
        //   - Waiter places an order every 300ms.
        //   - Chef cooks an order in 800ms.
        //   This means the board WILL fill up, and the waiter WILL block.
        //   This demonstrates the back-pressure mechanism of BlockingQueue.
        // ─────────────────────────────────────────────────────────────────────
        Runnable chef = () -> {
            try {
                int cooked = 0;
                while (true) {
                    // ┌──────────────────────────────────────────────────────┐
                    // │  queue.take()                                        │
                    // │                                                      │
                    // │  • Retrieves and removes the head of the queue.      │
                    // │  • If the queue is EMPTY, this call BLOCKS the       │
                    // │    chef thread until an order becomes available.     │
                    // │  • Once an item arrives, the thread is unparked      │
                    // │    and continues execution.                          │
                    // └──────────────────────────────────────────────────────┘
                    String order = orderBoard.take();

                    // Check for poison pill — exit gracefully
                    if (order.equals(POISON_PILL)) {
                        System.out.println("🔒 [CHEF]     Received kitchen-close signal. Shutting down.");
                        break;
                    }

                    cooked++;
                    System.out.println("👨‍🍳 [CHEF]     Cooking  → " + order + " ...");

                    Thread.sleep(800); // Simulate cooking time (slower than order placement)

                    System.out.println("🍛 [CHEF]     Ready!   ✓ " + order
                            + "  (" + cooked + "/" + TOTAL_ORDERS + " done)");
                }

                System.out.println("\n📊 [CHEF]     Total orders cooked: " + cooked);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("❌ [CHEF] Interrupted: " + e.getMessage());
            }
        };

        // ─────────────────────────────────────────────────────────────────────
        // THREAD CREATION & LIFECYCLE
        //
        // We create two threads and start them. The main thread then waits
        // for both to finish using thread.join().
        //
        // join() ensures the main method doesn't exit prematurely — it blocks
        // the calling thread (main) until the target thread terminates.
        // ─────────────────────────────────────────────────────────────────────
        Thread waiterThread = new Thread(waiter, "Waiter-Thread");
        Thread chefThread   = new Thread(chef,   "Chef-Thread");

        waiterThread.start();
        chefThread.start();

        // Wait for both threads to complete
        waiterThread.join();
        chefThread.join();

        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("  🏁 Restaurant closed. All orders processed.");
        System.out.println("═══════════════════════════════════════════════════════");
    }
}
