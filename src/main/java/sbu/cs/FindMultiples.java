package sbu.cs;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FindMultiples {

    private static class Task implements Runnable {
        private static final Set<Integer> multiples = new HashSet<>();
        private final int num;

        public Task(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            if (num % 3 == 0 || num % 5 == 0 || num % 7 == 0) {
                synchronized (multiples) {
                    multiples.add(num);
                }
            }
        }
    }

    public int getSum(int n) {
        int sum = 0;

        // Create a thread pool with 10 threads
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Create tasks for each multiple of 3, 5, or 7 in the range [1, n]
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 || i % 5 == 0 || i % 7 == 0) {
                Runnable task = new Task(i);
                executor.submit(task);
            }
        }

        // Wait for all tasks to finish
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Sum up the unique multiples
        for (int i : Task.multiples) {
            sum += i;
        }

        return sum;
    }

    public static void main(String[] args) {
        FindMultiples finder = new FindMultiples();
        int n = 10;
        int sum = finder.getSum(n);
        System.out.println("Sum of multiples of 3, 5, or 7 in the range [1, " + n + "]: " + sum);
    }
}
