package com.code.examples.thread;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadApp extends Thread {

  private AtomicBoolean running = new AtomicBoolean(true);
  private final AtomicInteger count = new AtomicInteger(1);

  public void run() {
    while (running.get()) {
      try {
        System.out.println(
            "Try block executed -- " + count.get() + " " + Thread.currentThread().getName());

        if (count.incrementAndGet() > 3) {
          break;
        }
        Thread.sleep(1000l);
      } catch (Throwable t) {
        running.set(false);
        Thread.currentThread().interrupt(); // Restore status
        break; // Exit the loop gracefully
      }
    }
  }

  public static void main(String[] args) {
    MyThreadApp myThreadApp1 = new MyThreadApp();
    myThreadApp1.setName("myThreadApp1");
    myThreadApp1.start();

    MyThreadApp myThreadApp2 = new MyThreadApp();
    myThreadApp2.setName("myThreadApp2");
    myThreadApp2.setDaemon(true);
    myThreadApp2.start();
    System.out.println(Thread.currentThread().getName());

    Runtime.getRuntime()
        .addShutdownHook(
            new Thread(
                () -> {
                  System.out.println("JVM is shutting down. Cleaning up resources...");
                }));
  }

  protected void finalize() {
    System.out.println("finalize " + Thread.currentThread().getName());
  }
}
