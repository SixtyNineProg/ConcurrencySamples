package ru.clevertec.courses.syncro.callable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;
import ru.clevertec.courses.ConcurrentUtils;

public class Reentrant {

  private static final int NUM_INCREMENTS = 10000;
  private static final ReentrantLock lock = new ReentrantLock();
  private static int count = 0;

  private static void increment() {
    lock.lock();
    try {
      count++;
    } finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) {
    testLock();
  }

  private static void testLock() {
    count = 0;

    ExecutorService executor = Executors.newFixedThreadPool(6);

    IntStream.range(0, NUM_INCREMENTS).forEach(i -> executor.submit(Reentrant::increment));

    ConcurrentUtils.stop(executor);

    System.out.println(count);
  }
}
